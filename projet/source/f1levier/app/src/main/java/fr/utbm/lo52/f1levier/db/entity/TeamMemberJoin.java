package fr.utbm.lo52.f1levier.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "team_member",
        primaryKeys = { "team_id", "member_id" },
        foreignKeys = {
                @ForeignKey(entity = Team.class,
                        parentColumns = "id",
                        childColumns = "team_id"),
                @ForeignKey(entity = Participant.class,
                        parentColumns = "id",
                        childColumns = "member_id")
        })
public class TeamMemberJoin {

    @ColumnInfo(name="team_id")
    public final int teamId;

    @ColumnInfo(name="member_id")
    public final int memberId;

    public TeamMemberJoin(final int teamId, final int memberId) {
        this.teamId = teamId;
        this.memberId = memberId;
    }
}
