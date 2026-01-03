package com.guibedan.jbank.repository;

import com.guibedan.jbank.entity.Wallet;
import com.guibedan.jbank.repository.dto.StatementView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    String SQL_STATEMENT = """
            SELECT
            	BIN_TO_UUID(tt.id) as statement_id,
            	"transfer" as type,
            	tt.transfer_value as statement_value,
            	BIN_TO_UUID(tt.wallet_receiver_id) as wallet_receiver,
            	BIN_TO_UUID(tt.wallet_sender_id) as wallet_sender,
            	tt.transfer_date_time as statement_date_time
            FROM
            	tb_transfers tt
            WHERE
                tt.wallet_receiver_id = ?1 OR tt.wallet_sender_id = ?1
            UNION ALL
            SELECT
            	BIN_TO_UUID(td.id) as statement_id,
            	"deposit" as type,
            	td.deposit_value as statement_value,
            	BIN_TO_UUID(td.wallet_id) as wallet_receiver,
            	NULL as wallet_sender,
            	td.deposit_date_time as statement_date_time
            FROM
            	tb_deposits td
            WHERE
                td.wallet_id = ?1
            """;

    String SQL_COUNT_STATEMENT = "SELECT COUNT(*) FROM (" + SQL_STATEMENT + ") total";

    boolean existsByCpfOrEmail(String cpf, String email);

    @Query(nativeQuery = true, value = SQL_STATEMENT, countQuery = SQL_COUNT_STATEMENT)
    Page<StatementView> findStatements(UUID walletId, Pageable pageRequest);

}
