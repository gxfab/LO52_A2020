package fr.utbm.lo52.f1levier.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import fr.utbm.lo52.f1levier.db.converter.DateConverter;
import fr.utbm.lo52.f1levier.db.dao.MeasuredTimeDao;
import fr.utbm.lo52.f1levier.db.dao.ParticipantDao;
import fr.utbm.lo52.f1levier.db.dao.RaceDao;
import fr.utbm.lo52.f1levier.db.dao.TeamDao;
import fr.utbm.lo52.f1levier.db.dao.TeamMemberJoinDao;
import fr.utbm.lo52.f1levier.db.entity.MeasuredTime;
import fr.utbm.lo52.f1levier.db.entity.Participant;
import fr.utbm.lo52.f1levier.db.entity.Race;
import fr.utbm.lo52.f1levier.db.entity.Team;
import fr.utbm.lo52.f1levier.db.entity.TeamMemberJoin;

@Database(
        entities = {
                Participant.class, Team.class, TeamMemberJoin.class, Race.class, MeasuredTime.class
        },
        version = 1,
        exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ParticipantDao participantDao();

    public abstract RaceDao raceDao();

    public abstract TeamDao teamDao();

    public abstract TeamMemberJoinDao teamMemberJoinDao();

    public abstract MeasuredTimeDao measuredTimeDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
