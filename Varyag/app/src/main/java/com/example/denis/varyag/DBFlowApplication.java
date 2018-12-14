package com.example.denis.varyag;

import android.app.Application;

import com.dbflow5.config.FlowManager;
import com.dbflow5.database.DatabaseWrapper;

public class DBFlowApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        FlowManager.init(this);

        EventModel evModel = new EventModel();

        evModel.setTitle("Вечерние тренировки в хоккейной школе Варяга");
        evModel.setImg("news_hockey");
        evModel.setDefine("Мы снова набираем вечернюю группу по тренировкам хоккею! Лучшие тренера Европы, 2 часа интенсивной работы, индивидуальный подход к каждому ученику. Принимаем желающих всех возрастов!");

        //        evModel.save(true);
        DatabaseWrapper dw = FlowManager.getWritableDatabase(AppDatabase.class);
        FlowManager.getModelAdapter(EventModel.class).save(evModel, dw);

        evModel = new EventModel();

        evModel.setTitle("Открытие горнолыжных склонов!");
        evModel.setImg("news_sklon");
        evModel.setDefine("Друзья, завтра долгожданный для всех день! Открываем сезон на горнолыжных склонах и склонах для тюбинга! Горнолыжные работают 01.12 и 02.12 работают с 12.00 до 22.00. Каток завтра с 14.00 до 22.00. Ежедневный режим работы, цены и подробности по ссылке ..");
        FlowManager.getModelAdapter(EventModel.class).save(evModel, dw);

        evModel = new EventModel();

        evModel.setTitle("Мы наконец-то засыпали горку для тюбинга!");
        evModel.setImg("news_tubing");
        evModel.setDefine("В эти выходные мы рады приветствовать на нашей горке для тюбинга! Подарите своему ребенку незабываемые выходные на горках Варяга! Ждем всех с 10 до 22.00 каждый день..");
        FlowManager.getModelAdapter(EventModel.class).save(evModel, dw);
    }
}
