package org.example.nextcommerce.common.utils.errormessage;

public enum ErrorCode {
    UnknownError(0, "Unknown Error"),
    IOException(1, "IOException"),
    InvalidRequestContent(10, "Invalid request content"),

    Member(100, "Member Error"),
    MemberNotFound(101, "Member not found"),
    MemberPasswordMismatch(102, "Member password mismatch"),
    MemberEmailValidationFailed(103, "Member email validation failed"),
    MemberPwValidationFailed(104, "Member password validation failed"),

    Session(200, "Session Error"),
    SessionSaveFail(201, "Session save fail"),
    SessionIsNull(202, "Session is null"),
    SessionMemberIsNull(203, "Session member is null"),

    Posts(300, "Posts Error"),
    PostsNotFound(301, "Posts not found"),
    PostsDeleteFail(302, "Posts DB delete failed"),
    PostsUnAuthorized(303, "Post's author is not valid"),

    Images(400, "Images Error"),
    ImagesNotFound(401,"Images not found"),
    ImagesDeleteFail(402,"Images DB delete failed"),

    Products(500, "Products Error"),
    ProductsNotFound(501, "Products not found"),
    ProductsDeleteFail(502, "Products DB delete failed"),

    DB(700,"Database Error"),
    DBConnectionTimeOut(701, "Database connection time out"),
    DBInsertFail(702, "Database insert failed"),
    DBTransactionFail(703, "Database transaction failed"),
    DBUpdateFail(704, "Database update failed"),
    DBDeleteFail(705, "Database delete failed");



    private int code;
    private String description;
    private ErrorCode(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode(){
        return code;
    }
    public String getDescription(){
        return description;
    }

}
