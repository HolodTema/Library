package com.terabyte.library;

public class Constant {
    public static final int MODE_CREATING = 0;
    public static final int MODE_MODIFICATION = 1;
    public static final String INTENT_KEY_MODE = "IntentKeyMode";
    public static final String INTENT_KEY_BOOK_ID = "bookId";

    //we must have same names of columns in database and names of cv keys!
    public static final String CV_KEY_TITLE = "title";
    public static final String CV_KEY_AUTHOR = "author";
    public static final String CV_KEY_DESCRIPTION = "description";
    public static final String CV_KEY_IMAGE_BYTE_ARRAY = "imageByteArray";

    public static final String INTENT_KEY_SORTED_STRING = "intentKeySortedString";

}
