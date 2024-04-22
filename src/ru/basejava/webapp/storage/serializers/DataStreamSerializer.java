package ru.basejava.webapp.storage.serializers;

import ru.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializerStrategy {
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeWithException(dos, resume.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(dos, resume.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((StringSection) section).getText());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeWithException(dos, ((ListSection) section).getText(), dos::writeUTF);
                    case EXPERIENCE, EDUCATION ->
                            writeWithException(dos, ((CompanySection) section).getCompanies(), organization -> {
                                dos.writeUTF(organization.getTitle());
                                dos.writeUTF(organization.getWebsite());
                                writeWithException(dos, organization.getPeriod(), position -> {
                                    dos.writeUTF(position.getStartDate().toString());
                                    dos.writeUTF(position.getEndDate().toString());
                                    dos.writeUTF(position.getTitle());
                                    dos.writeUTF(position.getDescription());
                                });
                            });
                    default -> throw new IllegalStateException();
                }
            });

        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new StringSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int itemsSize = dis.readInt();
                        List<String> items = new ArrayList<>();
                        for (int j = 0; j < itemsSize; j++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(items));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int companiesSize = dis.readInt();
                        List<Company> companies = new ArrayList<>();
                        for (int j = 0; j < companiesSize; j++) {
                            String title = dis.readUTF();
                            String website = dis.readUTF();
                            int periodsSize = dis.readInt();
                            List<Period> periods = new ArrayList<>();
                            for (int k = 0; k < periodsSize; k++) {
                                periods.add(new Period(dis.readUTF(), dis.readUTF(), LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF())));
                            }
                            Company company = new Company(title, website, periods.toArray(new Period[0]));
                            companies.add(company);
                        }
                        resume.addSection(sectionType, new CompanySection(companies));
                    }
                    default -> throw new IllegalStateException();
                }
            }

            return resume;
        }
    }


    private interface ConsumerWriter<T> {
        void accept(T t) throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, ConsumerWriter<T> action) throws IOException {
        dos.writeInt(collection.size());
        Objects.requireNonNull(action);
        for (T t : collection) {
            action.accept(t);
        }
    }

    //correct?
    /*private <T> void readWithException(DataInputStream dis, ConsumerWriter<T> action) throws IOException {
        int size = dis.readInt();
        Objects.requireNonNull(action);
        for (int i = 0; i < size; i++) {
            action.accept(null);
        }
    }*/



}
