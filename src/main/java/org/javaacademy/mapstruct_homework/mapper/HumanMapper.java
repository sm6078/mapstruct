package org.javaacademy.mapstruct_homework.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.javaacademy.mapstruct_homework.dto.PersonCreditDto;
import org.javaacademy.mapstruct_homework.dto.PersonDriverLicenceDto;
import org.javaacademy.mapstruct_homework.dto.PersonInsuranceDto;
import org.javaacademy.mapstruct_homework.entity.Human;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper
public interface HumanMapper {
    @Mapping(target = "passportNumber", source = ".", qualifiedByName = "getPassportNumber")
    @Mapping(target = "salary", source = ".", qualifiedByName = "getSalary")
    @Mapping(target = "fullAddress", source = ".", qualifiedByName = "getFullAddress")
    @Mapping(target = "id", ignore = true)
    PersonCreditDto convertToCreditDto(Human human);

    @Named("getPassportNumber")
    default String getPassportNumber(final Human human) {
        return String.format("%s %s", human.getPassport().getSeries(),
                human.getPassport().getNumber());
    }

    @Named("getSalary")
    default String getSalary(final Human human) {
        return String.format("%s %s", human.getWork().getSalary().toString(),
                human.getWork().getCurrency().toString());

    }

    @Named("getFullAddress")
    default String getFullAddress(final Human human) {
        return String.format("%s%s%s%s%s%s",
                human.getLivingAddress().getRegion() == null ? ""
                        : human.getLivingAddress().getRegion() + " ",
                human.getLivingAddress().getCity() == null ? ""
                        : human.getLivingAddress().getCity() + " ",
                human.getLivingAddress().getStreet() == null ? ""
                        : human.getLivingAddress().getStreet() + " ",
                human.getLivingAddress().getHouse() == null ? ""
                        : human.getLivingAddress().getHouse() + " ",
                human.getLivingAddress().getBuilding() == null ? ""
                        : human.getLivingAddress().getBuilding() + " ",
                human.getLivingAddress().getFlat() == null ? ""
                        : human.getLivingAddress().getFlat()).trim();
    }

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullPassportData", source = ".", qualifiedByName = "getFullPassportData")
    @Mapping(target = "birthDate", source = ".", qualifiedByName = "getBirthDate")
    PersonDriverLicenceDto convertToDriverLicenceDto(Human human);

    @Named("getFullName")
    default String getFullName(final Human human) {
        return String.format("%s %s%s",
                human.getFirstName(),
                human.getLastName(),
                human.getMiddleName().isEmpty() ? human.getMiddleName()
                        : " " + human.getMiddleName()
        );
    }

    @Named("getFullPassportData")
    default String getFullPassportData(final Human human) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        return String.format("%s%s %s", human.getPassport().getSeries(),
                human.getPassport().getNumber(),
                human.getPassport().getIssueDate().format(dateTimeFormatter));
    }

    @Named("getBirthDate")
    default String getBirthDate(final Human human) {
        return String.format("%s.%s.%s", human.getBirthDay(),
                human.getBirthMonth(), human.getBirthYear());
    }

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullAddress", source = ".", qualifiedByName = "getFullAddress")
    @Mapping(target = "fullAge", source = ".", qualifiedByName = "getFullAge")
    PersonInsuranceDto convertToInsuranceDto(Human human);

    @Named("getFullAge")
    default Integer fullAge(final Human human) {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        // тест считает только по годам
        /*
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();
            if (human.getBirthMonth() < month) {
                year += 1;
            } else if (human.getBirthMonth() == month && human.getBirthDay() >= dayOfMonth) {
                year += 1;
            }
         */
        return year - human.getBirthYear();
    }
}
