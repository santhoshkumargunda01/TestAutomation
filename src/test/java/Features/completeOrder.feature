@PWAPOC
Feature: Complete user order


Scenario: Get Token detials
   Given URI for API Endpoint "http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/initiate"
    And I have the request headers defined as follows
      | Content-Type | application/json |      
    When I send a GET request to get token details
    Then I verify the response status code as "200" 
    #And I get token and guid details
         
      
Scenario: Get Vendor Products
   Given URI for API Endpoint "http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/2066/products/v1"
    And I have the request headers defined as follows
          | Content-Type | application/json |      
    When I send a GET request top get vendor products
    Then I verify the response status code as "200"    
  
  
Scenario: Create Cart Details
   Given URI for API Endpoint "http://pwa-stg.venuetize.com/PWA/Tbrst34sba/22/shoppingcart/v2"
    And I have the request headers defined as follows
      | Content-Type | application/json |      
    When I send a POST request with cart details
    Then I verify the response status code as "201"  
    
    
 Scenario: Fetch User Wallet
   Given URI for API Endpoint " http://pwa-stg.venuetize.com/PWA/Tbrst34sba/22/userbyig/+identityGuid+/wallet/v2 "
   And I have the request headers defined as follows
          | Content-Type | application/json |     
   When I send a GET request to fetch user wallet
   Then I verify the response status code as "200"   
 
 Scenario: checkoutInfo
   Given URI for API Endpoint " http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/userbyig/+identityGuid+/shoppingcart/+cartId+/checkoutinfo/v1 "
   And I have the request headers defined as follows
      | Content-Type | application/json |      
   When I send a GET request to get checck out information
   Then I verify the response status code as "200" 
 
 Scenario: Complete Order 
    Given URI for API Endpoint " http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/userbyig/+identityGuid+/shoppingcart/+cartId+/order/v2";
	And I have the request headers defined as follows
      | Content-Type | application/json |      
    When I send a POST request to complete order
    Then I verify the response status code as "200"
    
    
    
    
       
        
    

      
      
      
   
     
