package com.vegetablestrading;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.vegetablestrading.bean.UserInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO".
*/
public class UserInfoDao extends AbstractDao<UserInfo, Long> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property UserName = new Property(2, String.class, "userName", false, "USER_NAME");
        public final static Property UserPhone = new Property(3, String.class, "userPhone", false, "USER_PHONE");
        public final static Property PetType = new Property(4, String.class, "petType", false, "PET_TYPE");
        public final static Property PetStatus = new Property(5, String.class, "petStatus", false, "PET_STATUS");
        public final static Property PetSum = new Property(6, String.class, "petSum", false, "PET_SUM");
        public final static Property Deposit = new Property(7, String.class, "deposit", false, "DEPOSIT");
        public final static Property ExpirationTime = new Property(8, String.class, "expirationTime", false, "EXPIRATION_TIME");
        public final static Property ResidualIntegral = new Property(9, String.class, "residualIntegral", false, "RESIDUAL_INTEGRAL");
        public final static Property ResidualPickAmount = new Property(10, String.class, "residualPickAmount", false, "RESIDUAL_PICK_AMOUNT");
        public final static Property BoxNo = new Property(11, String.class, "boxNo", false, "BOX_NO");
        public final static Property RegistDate = new Property(12, String.class, "registDate", false, "REGIST_DATE");
        public final static Property UserEmail = new Property(13, String.class, "userEmail", false, "USER_EMAIL");
        public final static Property UserAddr = new Property(14, String.class, "userAddr", false, "USER_ADDR");
        public final static Property RefundAccountName = new Property(15, String.class, "refundAccountName", false, "REFUND_ACCOUNT_NAME");
        public final static Property RefundAccount = new Property(16, String.class, "refundAccount", false, "REFUND_ACCOUNT");
    }


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" TEXT NOT NULL ," + // 1: userId
                "\"USER_NAME\" TEXT," + // 2: userName
                "\"USER_PHONE\" TEXT," + // 3: userPhone
                "\"PET_TYPE\" TEXT," + // 4: petType
                "\"PET_STATUS\" TEXT," + // 5: petStatus
                "\"PET_SUM\" TEXT," + // 6: petSum
                "\"DEPOSIT\" TEXT," + // 7: deposit
                "\"EXPIRATION_TIME\" TEXT," + // 8: expirationTime
                "\"RESIDUAL_INTEGRAL\" TEXT," + // 9: residualIntegral
                "\"RESIDUAL_PICK_AMOUNT\" TEXT," + // 10: residualPickAmount
                "\"BOX_NO\" TEXT," + // 11: boxNo
                "\"REGIST_DATE\" TEXT," + // 12: registDate
                "\"USER_EMAIL\" TEXT," + // 13: userEmail
                "\"USER_ADDR\" TEXT," + // 14: userAddr
                "\"REFUND_ACCOUNT_NAME\" TEXT," + // 15: refundAccountName
                "\"REFUND_ACCOUNT\" TEXT);"); // 16: refundAccount
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUserId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String userPhone = entity.getUserPhone();
        if (userPhone != null) {
            stmt.bindString(4, userPhone);
        }
 
        String petType = entity.getPetType();
        if (petType != null) {
            stmt.bindString(5, petType);
        }
 
        String petStatus = entity.getPetStatus();
        if (petStatus != null) {
            stmt.bindString(6, petStatus);
        }
 
        String petSum = entity.getPetSum();
        if (petSum != null) {
            stmt.bindString(7, petSum);
        }
 
        String deposit = entity.getDeposit();
        if (deposit != null) {
            stmt.bindString(8, deposit);
        }
 
        String expirationTime = entity.getExpirationTime();
        if (expirationTime != null) {
            stmt.bindString(9, expirationTime);
        }
 
        String residualIntegral = entity.getResidualIntegral();
        if (residualIntegral != null) {
            stmt.bindString(10, residualIntegral);
        }
 
        String residualPickAmount = entity.getResidualPickAmount();
        if (residualPickAmount != null) {
            stmt.bindString(11, residualPickAmount);
        }
 
        String boxNo = entity.getBoxNo();
        if (boxNo != null) {
            stmt.bindString(12, boxNo);
        }
 
        String registDate = entity.getRegistDate();
        if (registDate != null) {
            stmt.bindString(13, registDate);
        }
 
        String userEmail = entity.getUserEmail();
        if (userEmail != null) {
            stmt.bindString(14, userEmail);
        }
 
        String userAddr = entity.getUserAddr();
        if (userAddr != null) {
            stmt.bindString(15, userAddr);
        }
 
        String refundAccountName = entity.getRefundAccountName();
        if (refundAccountName != null) {
            stmt.bindString(16, refundAccountName);
        }
 
        String refundAccount = entity.getRefundAccount();
        if (refundAccount != null) {
            stmt.bindString(17, refundAccount);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getUserId());
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String userPhone = entity.getUserPhone();
        if (userPhone != null) {
            stmt.bindString(4, userPhone);
        }
 
        String petType = entity.getPetType();
        if (petType != null) {
            stmt.bindString(5, petType);
        }
 
        String petStatus = entity.getPetStatus();
        if (petStatus != null) {
            stmt.bindString(6, petStatus);
        }
 
        String petSum = entity.getPetSum();
        if (petSum != null) {
            stmt.bindString(7, petSum);
        }
 
        String deposit = entity.getDeposit();
        if (deposit != null) {
            stmt.bindString(8, deposit);
        }
 
        String expirationTime = entity.getExpirationTime();
        if (expirationTime != null) {
            stmt.bindString(9, expirationTime);
        }
 
        String residualIntegral = entity.getResidualIntegral();
        if (residualIntegral != null) {
            stmt.bindString(10, residualIntegral);
        }
 
        String residualPickAmount = entity.getResidualPickAmount();
        if (residualPickAmount != null) {
            stmt.bindString(11, residualPickAmount);
        }
 
        String boxNo = entity.getBoxNo();
        if (boxNo != null) {
            stmt.bindString(12, boxNo);
        }
 
        String registDate = entity.getRegistDate();
        if (registDate != null) {
            stmt.bindString(13, registDate);
        }
 
        String userEmail = entity.getUserEmail();
        if (userEmail != null) {
            stmt.bindString(14, userEmail);
        }
 
        String userAddr = entity.getUserAddr();
        if (userAddr != null) {
            stmt.bindString(15, userAddr);
        }
 
        String refundAccountName = entity.getRefundAccountName();
        if (refundAccountName != null) {
            stmt.bindString(16, refundAccountName);
        }
 
        String refundAccount = entity.getRefundAccount();
        if (refundAccount != null) {
            stmt.bindString(17, refundAccount);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userPhone
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // petType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // petStatus
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // petSum
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // deposit
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // expirationTime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // residualIntegral
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // residualPickAmount
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // boxNo
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // registDate
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // userEmail
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // userAddr
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // refundAccountName
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16) // refundAccount
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.getString(offset + 1));
        entity.setUserName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserPhone(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPetType(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPetStatus(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPetSum(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDeposit(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setExpirationTime(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setResidualIntegral(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setResidualPickAmount(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setBoxNo(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setRegistDate(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUserEmail(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setUserAddr(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setRefundAccountName(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setRefundAccount(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
