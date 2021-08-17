package com.everis.mssavingaccount.service.impl;

import com.everis.mssavingaccount.entity.CreditCard;
import com.everis.mssavingaccount.entity.Customer;
import com.everis.mssavingaccount.entity.SavingAccount;
import com.everis.mssavingaccount.repository.SavingAccountRepository;
import com.everis.mssavingaccount.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SavingAccountServiceImpl implements SavingAccountService {

	private final WebClient webClient;
	private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    String uri = "http://localhost:8090/api";
    
	public SavingAccountServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
		this.webClient = WebClient.builder().baseUrl(this.uri).build();
		this.reactiveCircuitBreaker = circuitBreakerFactory.create("customerCredit");
	}
    
    @Autowired
    SavingAccountRepository savingAccountRepository;

    // Plan A CUSTOMER
    @Override
    public Mono<Customer> findCustomer(String id) {
		return reactiveCircuitBreaker.run(webClient.get().uri(this.uri + "/ms-customer/customer/find/{id}",id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Customer.class),
				throwable -> {
					return this.getDefaultCustomer();
				});
    }
    
    // Plan B CUSTOMER
  	public Mono<Customer> getDefaultCustomer() {
  		Mono<Customer> customer = Mono.just(new Customer("0", null, null,null,null,null,null,null));
  		return customer;
  	}
  	
  	
  	// Plan A - CREDITCARD
    @Override
    public Flux<CreditCard> findCreditCardByCustomer(String id) {
		return reactiveCircuitBreaker.run(webClient.get().uri(this.uri + "/ms-creditcard/creditcard/find/{id}",id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(CreditCard.class),
				throwable -> {
					return this.getDefaultCreditCard();
				});
    }
    
    // Plan B - CREDITCARD
  	public Flux<CreditCard> getDefaultCreditCard() {
  		Flux<CreditCard> creditCard = Flux.just(new CreditCard("0", null, null,null,null,null));
  		return creditCard;
  	}
    
    
    @Override
    public Mono<SavingAccount> create(SavingAccount savingAccount) {
        return savingAccountRepository.save(savingAccount);
    }

    @Override
    public Flux<SavingAccount> findAll() {
        return savingAccountRepository.findAll();
    }

    @Override
    public Mono<SavingAccount> findById(String id) {
        return savingAccountRepository.findById(id) ;
    }

    @Override
    public Mono<SavingAccount> update(SavingAccount savingAccount) {
        return savingAccountRepository.save(savingAccount);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return savingAccountRepository.findById(id)
                .flatMap(
                        deletectaAhorro -> savingAccountRepository.delete(deletectaAhorro)
                                .then(Mono.just(Boolean.TRUE))
                )
                .defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Mono<Long> findCustomerAccountBank(String id) {
        return savingAccountRepository.findByCustomerId(id).count();
    }
    
    @Override
    public Mono<SavingAccount> findByCardNumber(String number) {
        return savingAccountRepository.findByCardNumber(number);
    }

}
