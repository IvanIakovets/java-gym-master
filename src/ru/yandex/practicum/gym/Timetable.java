package ru.yandex.practicum.gym;

import java.util.*;
import java.util.List;

public class Timetable {

    private static final Map<DayOfWeek, TreeMap<TimeOfDay,ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public boolean addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek day = trainingSession.getDayOfWeek(); //выносим значение дня тренировки
        TimeOfDay time = trainingSession.getTimeOfDay(); //выносим значение времени тренировки

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> daySchedule;
        ArrayList<TrainingSession> timeTraining;

        if (!timetable.containsKey(day)) {
            timetable.put(day,new TreeMap<>());
        } // проверяем наличие дня в расписании, если такого нет, то создаем
        daySchedule = timetable.get(day);
        if (!daySchedule.containsKey(time)) {
            daySchedule.put(time, new ArrayList<>());
        } // проверяем наличие времени в расписании, если такого нет, то создаем
        timeTraining = daySchedule.get(time);
        for (TrainingSession existingSession : timeTraining) {
            if (trainingSession.getCoach().equals(existingSession.getCoach()) ||
                    trainingSession.getGroup().equals(existingSession.getGroup())) {
                return false;
            }
        }
        timeTraining.add(trainingSession);
        return true;//сохраняем занятие в расписании
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());// возвращает расписание за день, сложность при этом будет так как метод у HashMap работает за О(1)

    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> daySchedule;

        daySchedule = timetable.get(dayOfWeek); // получаем расписание по искомому дню, если такого нет создается пустая таблица
        return daySchedule.getOrDefault(timeOfDay, new ArrayList<>()); // возвращает список всех тренировок, если он пустой создает новый список сложность О(1)
    }

    public HashMap<Coach, Integer> getCountByCoaches() {
        HashMap<Coach, Integer> coachesTraining = new HashMap<>();

        for (DayOfWeek day : timetable.keySet()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> daySchedule = timetable.get(day);
            for (TimeOfDay time : daySchedule.navigableKeySet()) {
                ArrayList<TrainingSession> timeTraining = daySchedule.get(time);
                for (TrainingSession trainingSession : timeTraining) {
                    Coach coachFromTimetable = trainingSession.getCoach();
                    coachesTraining.put(coachFromTimetable, coachesTraining.getOrDefault(coachFromTimetable, 0) + 1);
                }
            }
        }

        return sortCoaches(coachesTraining);
    }

    public HashMap<Coach, Integer> sortCoaches(HashMap<Coach, Integer> coachesTraining) {
        List<Map.Entry<Coach, Integer>> newCoachesTraining = new ArrayList<>(coachesTraining.entrySet());

        newCoachesTraining.sort((entry1,entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        HashMap<Coach, Integer> sortedCoachesTraining = new HashMap<>();
        for (Map.Entry<Coach, Integer> entry : newCoachesTraining) {
            sortedCoachesTraining.put(entry.getKey(), entry.getValue());
        }
        return sortedCoachesTraining;
    }
}
