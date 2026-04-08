package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, sessionsOnMonday.size());
        Assertions.assertEquals(1, sessionsOnMonday.firstEntry().getValue().size());//Проверяем, что за понедельник вернулось одно занятие
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY)); //Проверка, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(1, sessionsOnMonday.size()); // Проверяем что в Пн занятие только в 1 время
        Assertions.assertEquals(1, sessionsOnMonday.firstEntry().getValue().size()); //Проверяем, в это время только 1 занятие

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsOnThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        TimeOfDay timeOne = new TimeOfDay( 20, 0);
        TimeOfDay timeTwo = new TimeOfDay( 13, 0);

        Assertions.assertEquals(2, sessionsOnThursday.size()); // Проверка, что за четверг есть два занятия
        Assertions.assertEquals(1, sessionsOnThursday.get(timeOne).size()); // Проверка, только 1 занятия в данное время
        Assertions.assertEquals(1, sessionsOnThursday.get(timeTwo).size()); // Проверка, только 1 занятия в данное время

        ArrayList<TimeOfDay> thursdayTimeList = new ArrayList<>(sessionsOnThursday.descendingKeySet());
        Assertions.assertTrue(thursdayTimeList.get(0).equals(timeOne) && thursdayTimeList.get(1).equals(timeTwo));

        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));// Проверка, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TimeOfDay timeOne = new TimeOfDay( 13, 0);
        TimeOfDay timeTwo = new TimeOfDay( 14, 0);

        ArrayList<TrainingSession> sessionsOnMondayAndTimeOne = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOne);
        ArrayList<TrainingSession> sessionsOnMondayAndTimeTwo = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeTwo);


        Assertions.assertEquals(1, sessionsOnMondayAndTimeOne.size()); //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertNull(sessionsOnMondayAndTimeTwo); //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coachOne,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Coach coachTwo = new Coach("Мамбов", "Мумбай", "Тагирович");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coachTwo,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        TimeOfDay timeOne = new TimeOfDay( 13, 0);

        ArrayList<TrainingSession> sessionsOnMondayAndTimeOne = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, timeOne);

        Assertions.assertEquals(2, sessionsOnMondayAndTimeOne.size());  //Проверить, что за понедельник в 13:00 вернулось два занятие
    }

    @Test
    void testAddNewTrainingSessionOneCoach() {
        Timetable timetable = new Timetable();
        TimeOfDay time = new TimeOfDay(13, 0);

        Group groupOne = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSessionOne = new TrainingSession(groupOne, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));


        Assertions.assertTrue(timetable.addNewTrainingSession(singleTrainingSessionOne)); // Проверяем, что добавление завершилось успешно

        Group groupTwo = new Group("Акробатика для взрослых", Age.ADULT, 60);
        TrainingSession singleTrainingSessionTwo = new TrainingSession(groupTwo, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertFalse(timetable.addNewTrainingSession(singleTrainingSessionTwo)); // Проверяем, что добавление завершилось с ошибкой

        ArrayList<TrainingSession> sessionsOnMondayAndTimeOne = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time);
        Assertions.assertEquals(1, sessionsOnMondayAndTimeOne.size()); // Проверяем, что добавлено только 1 занятие

    }

    @Test
    void testAddNewTrainingSessionOneGroup() {
        Timetable timetable = new Timetable();
        TimeOfDay time = new TimeOfDay(13, 0);

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSessionOne = new TrainingSession(group, coachOne,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));


        Assertions.assertTrue(timetable.addNewTrainingSession(singleTrainingSessionOne)); // Проверяем, что добавление завершилось успешно

        Coach coachTwo = new Coach("Николаев", "Федор", "Игнатьевич");
        TrainingSession singleTrainingSessionTwo = new TrainingSession(group, coachTwo,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertFalse(timetable.addNewTrainingSession(singleTrainingSessionTwo)); // Проверяем, что добавление завершилось с ошибкой

        ArrayList<TrainingSession> sessionsOnMondayAndTimeOne = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time);
        Assertions.assertEquals(1, sessionsOnMondayAndTimeOne.size()); // Проверяем, что добавлено только 1 занятие
    }

    @Test
    void testGetCountByCoachesSingleAtDay() {
        Timetable timetable = new Timetable();

        Group groupOne = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSessionOne = new TrainingSession(groupOne, coachOne,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSessionOne);

        Group groupTwo = new Group("Акробатика для Взрослых", Age.ADULT, 60);
        Coach coachTwo = new Coach("Петров", "Федор", "Ануфриев");
        TrainingSession singleTrainingSessionTwo = new TrainingSession(groupTwo, coachTwo,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSessionTwo);

        Assertions.assertEquals(2,timetable.getCountByCoaches().size()); // Проверяем есть ли 2 тренера в списке
        Assertions.assertEquals(1,timetable.getCountByCoaches().get(coachOne)); // Проверяем что у тренера есть 1 тренировка
        Assertions.assertTrue(timetable.getCountByCoaches().containsKey(coachOne)); // Проверяем добавился ли тренер в список
        Assertions.assertEquals(1,timetable.getCountByCoaches().get(coachTwo)); // Проверяем что у тренера есть 1 тренировка
        Assertions.assertTrue(timetable.getCountByCoaches().containsKey(coachTwo)); // Проверяем добавился ли тренер в список
    }

    @Test
    void testGetCountByCoachesMultipleAtDay() {
        Timetable timetable = new Timetable();

        Group groupOne = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSessionOne = new TrainingSession(groupOne, coachOne,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSessionOne);

        Group groupTwo = new Group("Акробатика для Взрослых", Age.ADULT, 60);
        TrainingSession singleTrainingSessionTwo = new TrainingSession(groupTwo, coachOne,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(singleTrainingSessionTwo);

        Assertions.assertEquals(1,timetable.getCountByCoaches().size()); // Проверяем 1 ли тренер в списке
        Assertions.assertEquals(2,timetable.getCountByCoaches().get(coachOne)); // Проверяем что у тренера есть 2 тренировки
        Assertions.assertTrue(timetable.getCountByCoaches().containsKey(coachOne)); // Проверяем добавился ли тренер в список

    }

    @Test
    void testGetCountByCoachesMultipleAtDayAndTime() {
        Timetable timetable = new Timetable();

        Group groupOne = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coachOne = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSessionOne = new TrainingSession(groupOne, coachOne,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSessionOne);

        Group groupTwo = new Group("Акробатика для Взрослых", Age.ADULT, 60);
        Coach coachTwo = new Coach("Петров", "Федор", "Ануфриев");
        TrainingSession singleTrainingSessionTwo = new TrainingSession(groupTwo, coachTwo,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSessionTwo);

        Assertions.assertEquals(2,timetable.getCountByCoaches().size()); // Проверяем есть ли 2 тренера в списке
        Assertions.assertEquals(1,timetable.getCountByCoaches().get(coachOne)); // Проверяем что у тренера есть 1 тренировка
        Assertions.assertTrue(timetable.getCountByCoaches().containsKey(coachOne)); // Проверяем добавился ли тренер в список
        Assertions.assertEquals(1,timetable.getCountByCoaches().get(coachTwo)); // Проверяем что у тренера есть 1 тренировка
        Assertions.assertTrue(timetable.getCountByCoaches().containsKey(coachTwo)); // Проверяем добавился ли тренер в список
    }

    @Test
    void testGetCountByCoachesSortResult() {
        Timetable timetable = new Timetable();

        Coach coachOne = new Coach("Петров", "Анатолий", "Митрофанович");

        Group groupAdultFlipOne = new Group("Заднее сальто для начинающих", Age.ADULT, 90);
        TrainingSession fridayAdultTrainingSessionFlip = new TrainingSession(groupAdultFlipOne, coachOne,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(fridayAdultTrainingSessionFlip);

        Coach coachTwo = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdultAcro = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSessionAcro = new TrainingSession(groupAdultAcro, coachTwo,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSessionAcro);

        Group groupChildAcro = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSessionAcro = new TrainingSession(groupChildAcro, coachTwo,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSessionAcro = new TrainingSession(groupChildAcro, coachTwo,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSessionAcro = new TrainingSession(groupChildAcro, coachTwo,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSessionAcro);
        timetable.addNewTrainingSession(thursdayChildTrainingSessionAcro);
        timetable.addNewTrainingSession(saturdayChildTrainingSessionAcro);

        Coach coachThree = new Coach("Момов", "Николай", "Леопольдович");

        Group groupAdultFlipTwo = new Group("Заднее сальто для продолжающих", Age.ADULT, 90);
        TrainingSession wednesdayAdultTrainingSessionFlip = new TrainingSession(groupAdultFlipTwo, coachThree,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(wednesdayAdultTrainingSessionFlip);

        Group groupChildFlip = new Group("Заднее сальто для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSessionFlip = new TrainingSession(groupChildFlip, coachThree,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession saturdayChildTrainingSessionFlip = new TrainingSession(groupChildFlip, coachThree,
                DayOfWeek.SATURDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSessionFlip);
        timetable.addNewTrainingSession(saturdayChildTrainingSessionFlip);

        HashMap<Coach, Integer> countByCoaches = timetable.getCountByCoaches();
        ArrayList<Coach> coachArrayList = new ArrayList<>(countByCoaches.keySet());
        Assertions.assertEquals(coachTwo, coachArrayList.get(0));
        Assertions.assertEquals(coachThree, coachArrayList.get(1));
        Assertions.assertEquals(coachOne, coachArrayList.get(2));
    }
}
