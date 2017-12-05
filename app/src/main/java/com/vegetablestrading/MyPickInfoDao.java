package com.vegetablestrading;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.vegetablestrading.bean.MyPickInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_PICK_INFO".
*/
public class MyPickInfoDao extends AbstractDao<MyPickInfo, Long> {

    public static final String TABLENAME = "MY_PICK_INFO";

    /**
     * Properties of entity MyPickInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PetName = new Property(1, String.class, "petName", false, "PET_NAME");
        public final static Property Pick_Time = new Property(2, String.class, "pick_Time", false, "PICK__TIME");
        public final static Property PickPeopleNumber = new Property(3, String.class, "pickPeopleNumber", false, "PICK_PEOPLE_NUMBER");
        public final static Property ResidualPickAmount = new Property(4, String.class, "residualPickAmount", false, "RESIDUAL_PICK_AMOUNT");
        public final static Property OperatingPeople = new Property(5, String.class, "operatingPeople", false, "OPERATING_PEOPLE");
        public final static Property OperateTime = new Property(6, String.class, "operateTime", false, "OPERATE_TIME");
        public final static Property OperateNote = new Property(7, String.class, "operateNote", false, "OPERATE_NOTE");
    }


    public MyPickInfoDao(DaoConfig config) {
        super(config);
    }
    
    public MyPickInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_PICK_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PET_NAME\" TEXT NOT NULL ," + // 1: petName
                "\"PICK__TIME\" TEXT," + // 2: pick_Time
                "\"PICK_PEOPLE_NUMBER\" TEXT," + // 3: pickPeopleNumber
                "\"RESIDUAL_PICK_AMOUNT\" TEXT," + // 4: residualPickAmount
                "\"OPERATING_PEOPLE\" TEXT," + // 5: operatingPeople
                "\"OPERATE_TIME\" TEXT," + // 6: operateTime
                "\"OPERATE_NOTE\" TEXT);"); // 7: operateNote
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_PICK_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyPickInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPetName());
 
        String pick_Time = entity.getPick_Time();
        if (pick_Time != null) {
            stmt.bindString(3, pick_Time);
        }
 
        String pickPeopleNumber = entity.getPickPeopleNumber();
        if (pickPeopleNumber != null) {
            stmt.bindString(4, pickPeopleNumber);
        }
 
        String residualPickAmount = entity.getResidualPickAmount();
        if (residualPickAmount != null) {
            stmt.bindString(5, residualPickAmount);
        }
 
        String operatingPeople = entity.getOperatingPeople();
        if (operatingPeople != null) {
            stmt.bindString(6, operatingPeople);
        }
 
        String operateTime = entity.getOperateTime();
        if (operateTime != null) {
            stmt.bindString(7, operateTime);
        }
 
        String operateNote = entity.getOperateNote();
        if (operateNote != null) {
            stmt.bindString(8, operateNote);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyPickInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPetName());
 
        String pick_Time = entity.getPick_Time();
        if (pick_Time != null) {
            stmt.bindString(3, pick_Time);
        }
 
        String pickPeopleNumber = entity.getPickPeopleNumber();
        if (pickPeopleNumber != null) {
            stmt.bindString(4, pickPeopleNumber);
        }
 
        String residualPickAmount = entity.getResidualPickAmount();
        if (residualPickAmount != null) {
            stmt.bindString(5, residualPickAmount);
        }
 
        String operatingPeople = entity.getOperatingPeople();
        if (operatingPeople != null) {
            stmt.bindString(6, operatingPeople);
        }
 
        String operateTime = entity.getOperateTime();
        if (operateTime != null) {
            stmt.bindString(7, operateTime);
        }
 
        String operateNote = entity.getOperateNote();
        if (operateNote != null) {
            stmt.bindString(8, operateNote);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MyPickInfo readEntity(Cursor cursor, int offset) {
        MyPickInfo entity = new MyPickInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // petName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // pick_Time
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // pickPeopleNumber
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // residualPickAmount
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // operatingPeople
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // operateTime
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // operateNote
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyPickInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPetName(cursor.getString(offset + 1));
        entity.setPick_Time(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPickPeopleNumber(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setResidualPickAmount(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOperatingPeople(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOperateTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOperateNote(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MyPickInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MyPickInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MyPickInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
