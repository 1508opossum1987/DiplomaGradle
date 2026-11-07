package org.example.diplomagradle.datamodel.entity.wagon;

//тип грузовых операций с вагонами
public enum LoadingType {
    NONE, //нет грузовых операций, для всех, кроме местных
    LOADING, //только погрузка
    UNLOADING, //только выгрузка
    BOTH, //выгрузка и погрузка - сдвоенная операция
}
