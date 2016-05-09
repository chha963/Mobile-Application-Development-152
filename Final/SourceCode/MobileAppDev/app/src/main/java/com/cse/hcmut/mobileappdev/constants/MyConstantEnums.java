package com.cse.hcmut.mobileappdev.constants;

/**
 * Created by dinhn on 3/6/2016.
 */
public class MyConstantEnums {

    public enum Sort {
        ALL("Tất cả"),
        PRICE_ASC("Giá tăng dần"),
        PRICE_DESC("Giá giảm dần"),
        DATE_POST_DESC("Ngày đăng gần đây nhất"),
        DATE_EXPIRED_DESC("Các sản phẩm sắp hết hạn");

        private String mLabel;

        Sort(String label) {
            mLabel = label;
        }

        public String getTitle() {
            return mLabel;
        }

        public static String[] getArrayValues() {
            Sort[] districts = values();
            String[] names = new String[districts.length];

            for (int i = 0; i < districts.length; i++) {
                names[i] = districts[i].getTitle();
            }
            return names;
        }
    }

    public enum District {
        QUAN_1("Quận 1"),
        QUAN_2("Quận 2"),
        QUAN_3("Quận 3"),
        QUAN_4("Quận 4"),
        QUAN_5("Quận 5"),
        QUAN_6("Quận 6"),
        QUAN_7("Quận 7"),
        QUAN_8("Quận 8"),
        QUAN_9("Quận 9"),
        QUAN_10("Quận 10"),
        QUAN_11("Quận 11"),
        QUAN_12("Quận 12");

        private String mLabel;

        District(String label) {
            mLabel = label;
        }

        public String getTitle() {
            return mLabel;
        }

        public static String[] getArrayValues() {
            District[] districts = values();
            String[] names = new String[districts.length];

            for (int i = 0; i < districts.length; i++) {
                names[i] = districts[i].getTitle();
            }
            return names;
        }
    }

    public enum Ward {
        PHUONG_1("Phường 1"),
        PHUONG_2("Phường 2"),
        PHUONG_3("Phường 3"),
        PHUONG_4("Phường 4"),
        PHUONG_5("Phường 5"),
        PHUONG_6("Phường 6"),
        PHUONG_7("Phường 7"),
        PHUONG_8("Phường 8"),
        PHUONG_9("Phường 9"),
        PHUONG_10("Phường 10");

        private String mLabel;

        Ward(String label) {
            mLabel = label;
        }

        public String getTitle() {
            return mLabel;
        }

        public static String[] getArrayValues() {
            Ward[] wards = values();
            String[] names = new String[wards.length];

            for (int i = 0; i < wards.length; i++) {
                names[i] = wards[i].getTitle();
            }
            return names;
        }
    }

    public enum ProvinceCity {
        TP_HO_CHI_MINH("TP.Hồ Chí Minh"),
        TP_CAN_THO("TP.Cần Thơ"),
        TP_VUNG_TAU("TP.Vũng Tàu"),
        HA_NOI("Hà Nội");

        private String mLabel;

        ProvinceCity(String label) {
            mLabel = label;
        }

        public String getTitle() {
            return mLabel;
        }

        public static String[] getArrayValues() {
            ProvinceCity[] provinceCities = values();
            String[] names = new String[provinceCities.length];

            for (int i = 0; i < provinceCities.length; i++) {
                names[i] = provinceCities[i].getTitle();
            }
            return names;
        }
    }

}
