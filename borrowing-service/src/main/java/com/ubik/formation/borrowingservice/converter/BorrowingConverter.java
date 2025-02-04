package com.ubik.formation.borrowingservice.converter;

import com.ubik.formation.borrowingservice.dto.BorrowingDto;
import com.ubik.formation.borrowingservice.entity.Borrowing;

public class BorrowingConverter {
    public static BorrowingDto toDto(Borrowing borrowing) {
        if (borrowing == null) {
            return null;
        }
        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getBookId(),
                borrowing.getUserId(),
                borrowing.getBorrowDate(),
                borrowing.getReturnDate()
        );
    }

    public static Borrowing toEntity(BorrowingDto borrowingDto) {
        if (borrowingDto == null) {
            return null;
        }
        return new Borrowing(
                borrowingDto.getId(),
                borrowingDto.getBookId(),
                borrowingDto.getUserId(),
                borrowingDto.getBorrowDate(),
                borrowingDto.getReturnDate()
        );
    }
}
