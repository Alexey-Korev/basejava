package ru.basejava.webapp.storage.serializers;

import ru.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, () -> {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String contactValue = dis.readUTF();
                resume.addContact(contactType, contactValue);
            });

            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new StringSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> items = new ArrayList<>();
                        readWithException(dis, () -> items.add(dis.readUTF()));
                        resume.addSection(sectionType, new ListSection(items));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companies = new ArrayList<>();
                        readWithException(dis, () -> {
                            String title = dis.readUTF();
                            String website = dis.readUTF();
                            Company currentCompany = new Company(title, website);

                            readWithException(dis, () -> {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String periodTitle = dis.readUTF();
                                String periodDescription = dis.readUTF();
                                currentCompany.getPeriod().add(new Period(periodTitle, periodDescription, startDate, endDate));
                            });

                            companies.add(currentCompany);
                        });

                        resume.addSection(sectionType, new CompanySection(companies));
                    }
                    default -> throw new IllegalStateException("Invalid section type");
                }
            });

            return resume;
        }
    }



    private interface ConsumerWriter<T> {
        void accept(T t) throws IOException;
    }

    private interface ConsumerReader {
        void accept() throws IOException;
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, ConsumerWriter<T> action) throws IOException {
        dos.writeInt(collection.size());
        Objects.requireNonNull(action);
        for (T t : collection) {
            action.accept(t);
        }
    }

    private <T> void readWithException(DataInputStream dis, ConsumerReader action) throws IOException {
        int size = dis.readInt();
        Objects.requireNonNull(action);
        for (int i = 0; i < size; i++) {
            action.accept();
        }
    }



}
