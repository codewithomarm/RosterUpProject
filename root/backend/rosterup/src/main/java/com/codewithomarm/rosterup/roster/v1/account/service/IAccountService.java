package com.codewithomarm.rosterup.roster.v1.account.service;

import com.codewithomarm.rosterup.roster.v1.account.dto.request.CreateAccountRequest;
import com.codewithomarm.rosterup.roster.v1.account.dto.response.AccountResponse;
import com.codewithomarm.rosterup.tenant.v1.dto.request.UpdateTenantRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAccountService {
    public Page<AccountResponse> getAllAccounts(Pageable pageable);
    public Page<AccountResponse> getAllAccountsByTenantId(String tenantId, Pageable pageable);
    public Page<AccountResponse> getAllAccountsByTenantName(String tenantName, Pageable pageable);
    public AccountResponse getAccountById(String accountId);
    public AccountResponse getAccountByIdAndTenantId(String accountId, String tenantId);
    public AccountResponse getAccountByName(String accountName);
    public AccountResponse getAccountByNameAndTenantId(String accountName, String tenantId);
    public Page<AccountResponse> getAllActiveAccounts(Pageable pageable);
    public Page<AccountResponse> getAllActiveAccountsByTenantId(String tenantId, Pageable pageable);
    public Page<AccountResponse> getAllInactiveAccounts(Pageable pageable);
    public Page<AccountResponse> getAllInactiveAccountsByTenantId(String tenantId, Pageable pageable);
    public AccountResponse createAccount(CreateAccountRequest request);
    public AccountResponse updateAccount(UpdateTenantRequest request);
    public void deleteAccount(String accountId);
}
