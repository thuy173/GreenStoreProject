package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query("SELECT v FROM Voucher v WHERE v.status = true")
    List<Voucher> findActiveVoucher();

}
