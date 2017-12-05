package com.vegetablestrading;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.vegetablestrading.bean.MyApply;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_APPLY".
*/
public class MyApplyDao extends AbstractDao<MyApply, Long> {

    public static final String TABLENAME = "MY_APPLY";

    /**
     * Properties of entity MyApply.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PetName = new Property(1, String.class, "petName", false, "PET_NAME");
        public final static Property ApplyType = new Property(2, String.class, "applyType", false, "APPLY_TYPE");
        public final static Property ApplyInfo = new Property(3, String.class, "applyInfo", false, "APPLY_INFO");
        public final static Property Apply_startTime = new Property(4, String.class, "apply_startTime", false, "APPLY_START_TIME");
        public final static Property Apply_endTime = new Property(5, String.class, "apply_endTime", false, "APPLY_END_TIME");
        public final static Property Apply_Time = new Property(6, String.class, "apply_Time", false, "APPLY__TIME");
        public final static Property OperatingPeople = new Property(7, String.class, "operatingPeople", false, "OPERATING_PEOPLE");
        public final static Property OperateTime = new Property(8, String.class, "operateTime", false, "OPERATE_TIME");
        public final static Property OperateStatus = new Property(9, String.class, "operateStatus", false, "OPERATE_STATUS");
        public final static Property OperateNote = new Property(10, String.class, "operateNote", false, "OPERATE_NOTE");
    }


    public MyApplyDao(DaoConfig config) {
        super(config);
    }
    
    public MyApplyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_APPLY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PET_NAME\" TEXT NOT NULL ," + // 1: petName
                "\"APPLY_TYPE\" TEXT," + // 2: applyType
                "\"APPLY_INFO\" TEXT," + // 3: applyInfo
                "\"APPLY_START_TIME\" TEXT," + // 4: apply_startTime
                "\"APPLY_END_TIME\" TEXT," + // 5: apply_endTime
                "\"APPLY__TIME\" TEXT," + // 6: apply_Time
                "\"OPERATING_PEOPLE\" TEXT," + // 7: operatingPeople
                "\"OPERATE_TIME\" TEXT," + // 8: operateTime
                "\"OPERATE_STATUS\" TEXT," + // 9: operateStatus
                "\"OPERATE_NOTE\" TEXT);"); // 10: operateNote
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_APPLY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyApply entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPetName());
 
        String applyType = entity.getApplyType();
        if (applyType != null) {
            stmt.bindString(3, applyType);
        }
 
        String applyInfo = entity.getApplyInfo();
        if (applyInfo != null) {
            stmt.bindString(4, applyInfo);
        }
 
        String apply_startTime = entity.getApply_startTime();
        if (apply_startTime != null) {
            stmt.bindString(5, apply_startTime);
        }
 
        String apply_endTime = entity.getApply_endTime();
        if (apply_endTime != null) {
            stmt.bindString(6, apply_endTime);
        }
 
        String apply_Time = entity.getApply_Time();
        if (apply_Time != null) {
            stmt.bindString(7, apply_Time);
        }
 
        String operatingPeople = entity.getOperatingPeople();
        if (operatingPeople != null) {
            stmt.bindString(8, operatingPeople);
        }
 
        String operateTime = entity.getOperateTime();
        if (operateTime != null) {
            stmt.bindString(9, operateTime);
        }
 
        String operateStatus = entity.getOperateStatus();
        if (operateStatus != null) {
            stmt.bindString(10, operateStatus);
        }
 
        String operateNote = entity.getOperateNote();
        if (operateNote != null) {
            stmt.bindString(11, operateNote);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyApply entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPetName());
 
        String applyType = entity.getApplyType();
        if (applyType != null) {
            stmt.bindString(3, applyType);
        }
 
        String applyInfo = entity.getApplyInfo();
        if (applyInfo != null) {
            stmt.bindString(4, applyInfo);
        }
 
        String apply_startTime = entity.getApply_startTime();
        if (apply_startTime != null) {
            stmt.bindString(5, apply_startTime);
        }
 
        String apply_endTime = entity.getApply_endTime();
        if (apply_endTime != null) {
            stmt.bindString(6, apply_endTime);
        }
 
        String apply_Time = entity.getApply_Time();
        if (apply_Time != null) {
            stmt.bindString(7, apply_Time);
        }
 
        String operatingPeople = entity.getOperatingPeople();
        if (operatingPeople != null) {
            stmt.bindString(8, operatingPeople);
        }
 
        String operateTime = entity.getOperateTime();
        if (operateTime != null) {
            stmt.bindString(9, operateTime);
        }
 
        String operateStatus = entity.getOperateStatus();
        if (operateStatus != null) {
            stmt.bindString(10, operateStatus);
        }
 
        String operateNote = entity.getOperateNote();
        if (operateNote != null) {
            stmt.bindString(11, operateNote);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MyApply readEntity(Cursor cursor, int offset) {
        MyApply entity = new MyApply( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // petName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // applyType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // applyInfo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // apply_startTime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // apply_endTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // apply_Time
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // operatingPeople
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // operateTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // operateStatus
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // operateNote
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyApply entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPetName(cursor.getString(offset + 1));
        entity.setApplyType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setApplyInfo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setApply_startTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setApply_endTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setApply_Time(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOperatingPeople(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setOperateTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setOperateStatus(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setOperateNote(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MyApply entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MyApply entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MyApply entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
