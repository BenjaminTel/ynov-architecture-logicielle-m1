package com.ubik.formation.userservice.enumeration;

public enum MemberShipType {

    REGULAR("REGULAR"),
    PREMIUM("PRENIUM");

    private final String value;

    MemberShipType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MemberShipType fromString(String membershipType) {
        for (MemberShipType type : MemberShipType.values()) {
            if (type.getValue().equalsIgnoreCase(membershipType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + membershipType);
    }
}
