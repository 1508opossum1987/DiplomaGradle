package org.example.diplomagradle.datamodel.entity.wagon;

//варианты операций с вагонами
public enum WagonOperationType {
    ARRIVAL, //прибытие
    DEPARTURE, //отправление
    LOADING, //погрузка
    UNLOADING, //выгрузка
    REPAIR, // ремонт
    PROCESSING, //обработка
    SPOTTING, //подача
    REMOVAL, //уборка
    WAITING_SPOTTING, //ожидание подачи
    WAITING_REMOVAL, //ожидание уборки
    WAITING_REPAIR, //ожидание ремонта
    WAITING_OPER, //ожидание любой операции на станции
    DISBANDMENT, //расформирование
}
