/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.36-log : Database - oauth2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`oauth2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;

USE `oauth2`;

/*Table structure for table `oauth2_authorization` */

DROP TABLE IF EXISTS `oauth2_authorization`;

CREATE TABLE `oauth2_authorization` (
  `id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `registered_client_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `principal_name` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `authorization_grant_type` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `attributes` text COLLATE utf8mb4_bin,
  `state` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
  `authorization_code_value` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `authorization_code_issued_at` datetime DEFAULT NULL,
  `authorization_code_expires_at` datetime DEFAULT NULL,
  `authorization_code_metadata` text COLLATE utf8mb4_bin,
  `access_token_value` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `access_token_issued_at` datetime DEFAULT NULL,
  `access_token_expires_at` datetime DEFAULT NULL,
  `access_token_metadata` text COLLATE utf8mb4_bin,
  `access_token_type` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `access_token_scopes` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `oidc_id_token_value` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `oidc_id_token_issued_at` datetime DEFAULT NULL,
  `oidc_id_token_expires_at` datetime DEFAULT NULL,
  `oidc_id_token_metadata` text COLLATE utf8mb4_bin,
  `refresh_token_value` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `refresh_token_issued_at` datetime DEFAULT NULL,
  `refresh_token_expires_at` datetime DEFAULT NULL,
  `refresh_token_metadata` text COLLATE utf8mb4_bin,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_state` (`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `oauth2_authorization` */

insert  into `oauth2_authorization`(`id`,`registered_client_id`,`principal_name`,`authorization_grant_type`,`attributes`,`state`,`authorization_code_value`,`authorization_code_issued_at`,`authorization_code_expires_at`,`authorization_code_metadata`,`access_token_value`,`access_token_issued_at`,`access_token_expires_at`,`access_token_metadata`,`access_token_type`,`access_token_scopes`,`oidc_id_token_value`,`oidc_id_token_issued_at`,`oidc_id_token_expires_at`,`oidc_id_token_metadata`,`refresh_token_value`,`refresh_token_issued_at`,`refresh_token_expires_at`,`refresh_token_metadata`,`created_at`,`updated_at`) values ('02012ad6-05dc-4536-86fd-aa9c241e569e','client-1','user1','authorization_code','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"java.security.Principal\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"details\":{\"@class\":\"org.springframework.security.web.authentication.WebAuthenticationDetails\",\"remoteAddress\":\"127.0.0.1\",\"sessionId\":\"0779D970446140636A988A9A3A75996C\"},\"authenticated\":true,\"principal\":{\"@class\":\"org.springframework.security.core.userdetails.User\",\"password\":null,\"username\":\"user1\",\"authorities\":[\"java.util.Collections$UnmodifiableSet\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true},\"credentials\":null},\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\":{\"@class\":\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\",\"authorizationUri\":\"http://localhost:9000/oauth2/authorize\",\"authorizationGrantType\":{\"value\":\"authorization_code\"},\"responseType\":{\"value\":\"code\"},\"clientId\":\"messaging-client\",\"redirectUri\":\"http://www.baidu.com\",\"scopes\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]],\"state\":\"some-state\",\"additionalParameters\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"code_challenge\":\"23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE\",\"code_challenge_method\":\"S256\"},\"authorizationRequestUri\":\"http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.write&state=some-state&redirect_uri=http://www.baidu.com&code_challenge=23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE&code_challenge_method=S256\",\"attributes\":{\"@class\":\"java.util.Collections$UnmodifiableMap\"}},\"org.springframework.security.oauth2.server.authorization.OAuth2Authorization.AUTHORIZED_SCOPE\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]]}',NULL,'txc-acNZfcOa92SDyResfbENz2QpO4PiVYrDTVx0D9ycrUo9GK6u8_WOekpU338mBST3S1_nFxGXZX79eJMtKo6zdlYNeCZa69cV-oTDhUTqX7qEedFFH3JMASCv_r96','2022-03-19 16:37:27','2022-03-19 16:42:27','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-03-19 16:37:27','2022-03-19 16:37:27'),('137be428-881d-4a5b-8915-f1e376739933','client-1','user1','authorization_code','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"java.security.Principal\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"details\":{\"@class\":\"org.springframework.security.web.authentication.WebAuthenticationDetails\",\"remoteAddress\":\"127.0.0.1\",\"sessionId\":\"31EA1AF9BE92D2C78C6307E94B453F42\"},\"authenticated\":true,\"principal\":{\"@class\":\"org.springframework.security.core.userdetails.User\",\"password\":null,\"username\":\"user1\",\"authorities\":[\"java.util.Collections$UnmodifiableSet\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true},\"credentials\":null},\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\":{\"@class\":\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\",\"authorizationUri\":\"http://localhost:9000/oauth2/authorize\",\"authorizationGrantType\":{\"value\":\"authorization_code\"},\"responseType\":{\"value\":\"code\"},\"clientId\":\"messaging-client\",\"redirectUri\":\"http://www.baidu.com\",\"scopes\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]],\"state\":\"some-state\",\"additionalParameters\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"code_challenge\":\"23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE\",\"code_challenge_method\":\"S256\"},\"authorizationRequestUri\":\"http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.write&state=some-state&redirect_uri=http://www.baidu.com&code_challenge=23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE&code_challenge_method=S256\",\"attributes\":{\"@class\":\"java.util.Collections$UnmodifiableMap\"}},\"org.springframework.security.oauth2.server.authorization.OAuth2Authorization.AUTHORIZED_SCOPE\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]]}',NULL,'mLByYUrr5PDXB3f0I9NE26g-sREDW6Fogji5i3M6ywumQ3iBYcfC3i_s-r-PRrnMTUhC3h2FkxvpGnUiUk8T1CcYuLXswvQm-_YBwZGaQlgo8LkDoGYHwUu88jV16YUa','2022-03-19 17:03:31','2022-03-19 17:08:31','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-03-19 17:04:44','2022-03-19 17:04:44'),('36684d0e-143e-4cbd-be4c-a62b2ea410f0','client-1','user1','authorization_code','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"java.security.Principal\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"details\":{\"@class\":\"org.springframework.security.web.authentication.WebAuthenticationDetails\",\"remoteAddress\":\"127.0.0.1\",\"sessionId\":\"DFC1C9975F96A8329CF50EEC879858AA\"},\"authenticated\":true,\"principal\":{\"@class\":\"org.springframework.security.core.userdetails.User\",\"password\":null,\"username\":\"user1\",\"authorities\":[\"java.util.Collections$UnmodifiableSet\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true},\"credentials\":null},\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\":{\"@class\":\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\",\"authorizationUri\":\"http://localhost:9000/oauth2/authorize\",\"authorizationGrantType\":{\"value\":\"authorization_code\"},\"responseType\":{\"value\":\"code\"},\"clientId\":\"messaging-client\",\"redirectUri\":\"http://www.baidu.com\",\"scopes\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]],\"state\":\"some-state\",\"additionalParameters\":{\"@class\":\"java.util.Collections$UnmodifiableMap\"},\"authorizationRequestUri\":\"http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.write&state=some-state&redirect_uri=http://www.baidu.com\",\"attributes\":{\"@class\":\"java.util.Collections$UnmodifiableMap\"}},\"org.springframework.security.oauth2.server.authorization.OAuth2Authorization.AUTHORIZED_SCOPE\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]]}',NULL,'VXykrhT2aAe4_9xUOA3FgO3tSo6PYHwz0_i1HUdIgYVckC1sGRdUxkLcbbpPyLH__LNbugT0fytQh02skH4je0AtTIQzjjKmiQfhr-m_2JdlAKxi8LAcuSqgFckWAKpm','2022-03-19 11:39:20','2022-03-19 11:44:20','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-03-19 11:39:27','2022-03-19 11:39:27'),('41cc9410-93a2-4b61-84bf-f496e64de47b','client-1','user1','authorization_code','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\":{\"@class\":\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\",\"authorizationUri\":\"http://localhost:9000/oauth2/authorize\",\"authorizationGrantType\":{\"value\":\"authorization_code\"},\"responseType\":{\"value\":\"code\"},\"clientId\":\"messaging-client\",\"redirectUri\":\"http://www.baidu.com\",\"scopes\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]],\"state\":\"some-state\",\"additionalParameters\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"code_challenge\":\"23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE\",\"code_challenge_method\":\"S256\"},\"authorizationRequestUri\":\"http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.write&state=some-state&redirect_uri=http://www.baidu.com&code_challenge=23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE&code_challenge_method=S256\",\"attributes\":{\"@class\":\"java.util.Collections$UnmodifiableMap\"}},\"java.security.Principal\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"details\":{\"@class\":\"org.springframework.security.web.authentication.WebAuthenticationDetails\",\"remoteAddress\":\"127.0.0.1\",\"sessionId\":\"31EA1AF9BE92D2C78C6307E94B453F42\"},\"authenticated\":true,\"principal\":{\"@class\":\"org.springframework.security.core.userdetails.User\",\"password\":null,\"username\":\"user1\",\"authorities\":[\"java.util.Collections$UnmodifiableSet\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true},\"credentials\":null},\"org.springframework.security.oauth2.server.authorization.OAuth2Authorization.AUTHORIZED_SCOPE\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]]}',NULL,'8LhY5lGoXsM991tXLbvew5bx-jN0T_zN-IhYB-qPUCZRRiz6jpw_g2aILuonEskazxS2qowQzkZDEtH0PPrPDwUBwYKhpsTwXadm2k-PnuJMSe7GQqd5jKuh9vgofhxi','2022-03-19 16:57:05','2022-03-19 17:02:05','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":true}','eyJraWQiOiJiNzA5ZmU0My1jOGM2LTRkNWYtYTI2Ni05M2I1MWU2MjNiZjYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDc2ODAzNjUsInNjb3BlIjpbIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0NzY4MDY2NSwiaWF0IjoxNjQ3NjgwMzY1fQ.hHg11pINx4GJXNodH6jsEtVdRiPjATDDplFfVKoW4iqfsI3Q8wdRxuDDKt9x8Lez49xNhcqh_MIBQ1eRWwGqAYG-J5ZMlyH3SI0WUSp4qYk_EPxE2p64comZaoe5TUuTN-aCie5bQ1Pmm0MI9jb_J-tLn5bGV86vTl72JWDWAbjIMgw0KfnJeTLMP9bIiBV7f6-8tOrH4lzNiNUzbiAX27ZwY0-onKuD0l3sOPyiE159UKxYGobwn0T-_PEgZ3c7slBugiy6mpu3xnuecXnKqKr2s_OFRDcpV2RX1zYUu14zA5D2LAV7g-sMEzUSP2z9qciPrV8IAAq9ojDQ9Pb2_Q','2022-03-19 16:59:26','2022-03-19 17:04:26','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"user1\",\"aud\":[\"java.util.Collections$SingletonList\",[\"messaging-client\"]],\"nbf\":[\"java.time.Instant\",1647680365.662000000],\"scope\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]],\"iss\":[\"java.net.URL\",\"http://auth-server:9000\"],\"exp\":[\"java.time.Instant\",1647680665.662000000],\"iat\":[\"java.time.Instant\",1647680365.662000000]},\"metadata.token.invalidated\":false}','Bearer','message.write',NULL,NULL,NULL,NULL,'bt7SXMZOelDLwme8ulf7SzqEPmYNEiCr4DbIs-ad3qyfEETBohy1_ztz13rn1L4ARvaxSHbz4Gblh_Rypdlgm6FMATGlHVBPL5e39rJTfKGbOP_2N6lndOqIT9dRzsvA','2022-03-19 16:58:02','2022-03-19 17:58:02','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','2022-03-19 16:57:09','2022-03-19 16:59:26'),('44f5bfe6-8ac8-40c6-b0e8-348cb07e5aeb','client-1','messaging-client','client_credentials','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"org.springframework.security.oauth2.server.authorization.OAuth2Authorization.AUTHORIZED_SCOPE\":[\"java.util.Collections$UnmodifiableSet\",[\"message.read\",\"message.write\"]]}',NULL,NULL,NULL,NULL,NULL,'eyJraWQiOiJhOGFmOGM5NC00M2I3LTQ5YmMtYjQ1Yy00MGE1MzMyMTRkNjUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY0NzY3ODk5Niwic2NvcGUiOlsibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOlwvXC9hdXRoLXNlcnZlcjo5MDAwIiwiZXhwIjoxNjQ3Njc5Mjk2LCJpYXQiOjE2NDc2Nzg5OTZ9.In7_UEW9WWrRboxOaws2gAgw6Ocz9PwNCAmM9tAO-lthNJ5tvonOEGaiW0hyJXks2T-XH4PSMbsnscKftfzQSL9ec_jBK1rvkUBav-7kWJAZZxR5ZMj8jEl3bT_gacpiCnA0-4xbwd-a0YCKWV6BNf2J4TtnEcsq0VHHeV-lBco3aa67X-oL_IgF_8H4KO-QiV-dLBXGYjFr7K3WWtF5_BXEYuhFhX5eAMemkh6ssdXhm5WAhhaHVtPpX6oRbOmG_reAf2j5P2kEDJSooI5D628NapKVwrNA3kAJBAqlpCchjNKhY-9kIxx00T_qBEzX50kM8kSKtk-GLUKxDckBug','2022-03-19 16:36:37','2022-03-19 16:41:37','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"messaging-client\",\"aud\":[\"java.util.Collections$SingletonList\",[\"messaging-client\"]],\"nbf\":[\"java.time.Instant\",1647678996.782000000],\"scope\":[\"java.util.Collections$UnmodifiableSet\",[\"message.read\",\"message.write\"]],\"iss\":[\"java.net.URL\",\"http://auth-server:9000\"],\"exp\":[\"java.time.Instant\",1647679296.782000000],\"iat\":[\"java.time.Instant\",1647678996.782000000]},\"metadata.token.invalidated\":false}','Bearer','message.read,message.write',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-03-19 16:36:37','2022-03-19 16:36:37'),('90db8926-adcd-4c8a-b19d-5c22008431df','client-1','user1','authorization_code','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\":{\"@class\":\"org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest\",\"authorizationUri\":\"http://localhost:9000/oauth2/authorize\",\"authorizationGrantType\":{\"value\":\"authorization_code\"},\"responseType\":{\"value\":\"code\"},\"clientId\":\"messaging-client\",\"redirectUri\":\"http://www.baidu.com\",\"scopes\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]],\"state\":\"some-state\",\"additionalParameters\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"code_challenge\":\"23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE\",\"code_challenge_method\":\"S256\"},\"authorizationRequestUri\":\"http://localhost:9000/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.write&state=some-state&redirect_uri=http://www.baidu.com&code_challenge=23lwVh3xPX1ckZmTTzvoh6zY_L4gi2rvd4s9kKF9FQE&code_challenge_method=S256\",\"attributes\":{\"@class\":\"java.util.Collections$UnmodifiableMap\"}},\"java.security.Principal\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"details\":{\"@class\":\"org.springframework.security.web.authentication.WebAuthenticationDetails\",\"remoteAddress\":\"127.0.0.1\",\"sessionId\":\"31EA1AF9BE92D2C78C6307E94B453F42\"},\"authenticated\":true,\"principal\":{\"@class\":\"org.springframework.security.core.userdetails.User\",\"password\":null,\"username\":\"user1\",\"authorities\":[\"java.util.Collections$UnmodifiableSet\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ROLE_USER\"}]],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true},\"credentials\":null},\"org.springframework.security.oauth2.server.authorization.OAuth2Authorization.AUTHORIZED_SCOPE\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]]}',NULL,'U3xb3xknHZK21bh35XUW-F5KKplAea3Aa5RfUv5Yl6MG63O9Boo4uhUtXgqa3CK6yr8uG3XpJlneC38l0JRnWGcsAHyQRD4CQ9gcQsFy_0T4upL_sgVp09U9CicjsMVK','2022-03-19 16:53:47','2022-03-19 16:58:47','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":true}','eyJraWQiOiJiNzA5ZmU0My1jOGM2LTRkNWYtYTI2Ni05M2I1MWU2MjNiZjYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDc2ODAwODIsInNjb3BlIjpbIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0NzY4MDM4MiwiaWF0IjoxNjQ3NjgwMDgyfQ.MfFIm4rIzT6d4E40KGPRTHezRvVEEgcGSuQ7mgWFR13wrp95NNFKkcYAqv5je41H4apxiDMzzZyfrwIMWHMPBS1hMSLN2G1LUd27mvpxR1FzBaSW9u0NeI0CfsRA3q6aqbZMwe__iPXukZG2WMHrp2SDT4M_sjAhDd86a8x44q3VJXd-UvHa0VXgWu7za-BHTnbJFp9JrLJMBqKpD5h-yGGwOq60Jqh-R-SRPpIvoubJfv9IaAYdY9ZrKbz-1-FIubYNtjM6gTNsLuGiCl-sFqli24TXc8Jhx6C10K8S6xmVCN7obbom_rhmRXj6bmTg52fR_OtE0rvvou9m_AU7yA','2022-03-19 16:54:43','2022-03-19 16:59:43','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"user1\",\"aud\":[\"java.util.Collections$SingletonList\",[\"messaging-client\"]],\"nbf\":[\"java.time.Instant\",1647680082.894000000],\"scope\":[\"java.util.Collections$UnmodifiableSet\",[\"message.write\"]],\"iss\":[\"java.net.URL\",\"http://auth-server:9000\"],\"exp\":[\"java.time.Instant\",1647680382.894000000],\"iat\":[\"java.time.Instant\",1647680082.894000000]},\"metadata.token.invalidated\":false}','Bearer','message.write',NULL,NULL,NULL,NULL,'LdmGkWLNsCkP6bWcLYNrz9wOHOsZdT4xFB8x_KIw0Lq1RFlytlN4ExzgkFeDgFoVHCs4mizZekx_utUim4ikDRK7bgNkzIhRSuokFnh8F06UMzBdj7w03WMO99OIXxbO','2022-03-19 16:54:43','2022-03-19 17:54:43','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','2022-03-19 16:53:52','2022-03-19 16:54:48'),('fe1ac275-4929-4dcc-b48e-786972632081','client-1','messaging-client','password','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"java.security.Principal\":{\"@class\":\"example.authentication.OAuth2PasswordCredentialsAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[]],\"details\":{\"@class\":\"org.springframework.security.web.authentication.WebAuthenticationDetails\",\"remoteAddress\":\"127.0.0.1\",\"sessionId\":null},\"authenticated\":false,\"additionalParameters\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"password\":\"password\",\"username\":\"user1\"},\"scopes\":[\"java.util.Collections$UnmodifiableSet\",[]],\"username\":\"user1\",\"password\":\"password\",\"credentials\":\"\",\"principal\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[]],\"details\":{\"@class\":\"org.springframework.security.web.authentication.WebAuthenticationDetails\",\"remoteAddress\":\"127.0.0.1\",\"sessionId\":null},\"authenticated\":true,\"registeredClient\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.client.RegisteredClient\",\"id\":\"client-1\",\"clientId\":\"messaging-client\",\"clientIdIssuedAt\":1647524616.000000000,\"clientSecret\":\"{noop}secret\",\"clientSecretExpiresAt\":1647524627.000000000,\"clientName\":\"client-1\",\"clientAuthenticationMethods\":[\"java.util.Collections$UnmodifiableSet\",[{\"value\":\"client_secret_post\"},{\"value\":\"private_key_jwt\"},{\"value\":\"post\"},{\"value\":\"client_secret_jwt\"},{\"value\":\"none\"},{\"value\":\"basic\"},{\"value\":\"client_secret_basic\"}]],\"authorizationGrantTypes\":[\"java.util.Collections$UnmodifiableSet\",[{\"value\":\"refresh_token\"},{\"value\":\"client_credentials\"},{\"value\":\"password\"},{\"value\":\"authorization_code\"}]],\"redirectUris\":[\"java.util.Collections$UnmodifiableSet\",[\"http://127.0.0.1:8080/authorized\",\"http://www.baidu.com\",\"http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc\"]],\"scopes\":[\"java.util.Collections$UnmodifiableSet\",[\"message.read\",\"message.write\"]],\"clientSettings\":{\"settings\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true},\"requireProofKey\":false,\"requireAuthorizationConsent\":true,\"jwkSetUrl\":null,\"tokenEndpointAuthenticationSigningAlgorithm\":null},\"tokenSettings\":{\"settings\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000]},\"refreshTokenTimeToLive\":[\"java.time.Duration\",3600.000000000],\"reuseRefreshTokens\":true,\"accessTokenTimeToLive\":[\"java.time.Duration\",300.000000000],\"idTokenSignatureAlgorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"]}},\"clientAuthenticationMethod\":{\"value\":\"client_secret_basic\"},\"credentials\":\"secret\",\"additionalParameters\":{\"@class\":\"java.util.Collections$UnmodifiableMap\"},\"principal\":\"messaging-client\",\"name\":\"messaging-client\"},\"grantType\":{\"value\":\"password\"},\"name\":\"messaging-client\"},\"org.springframework.security.oauth2.server.authorization.OAuth2Authorization.AUTHORIZED_SCOPE\":[\"java.util.Collections$UnmodifiableSet\",[\"message.read\",\"message.write\"]]}',NULL,NULL,NULL,NULL,NULL,'eyJraWQiOiJhOGFmOGM5NC00M2I3LTQ5YmMtYjQ1Yy00MGE1MzMyMTRkNjUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImF1ZCI6Im1lc3NhZ2luZy1jbGllbnQiLCJuYmYiOjE2NDc2Nzg4ODYsInNjb3BlIjpbIm1lc3NhZ2UucmVhZCIsIm1lc3NhZ2Uud3JpdGUiXSwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTAwMCIsImV4cCI6MTY0NzY3OTE4NiwiaWF0IjoxNjQ3Njc4ODg2fQ.QHxSUSewqDPUd_wRuwaIzc_u_H0Yc6Qc2lF4Ewx8FRkW1YnkuTDqiM5ZcFNd69kDNx8Ef_dgD5z1v_IP333hRulfScv-F5sMqJenEWMMdBEymuefuFkxqzC4bUZLbPl4Z_BXqSLo5Y2FCkqjqbc5Qrzo5GNwcw2VvGSShwrDGcml1Kj8duINasJJeYbAQ-JH0Dxo8hDS0WWts5MWOAlSu6pKbcEzcrulYoCpGjHukeCPpQeJorn1Spb6KpkblOcTV78TBddol0lW7EU0azU-bCmvQFE368ZZnSLsOAWfDnDFeAz3DDOhBDBdsFl-6gEnb9JtFiOSX4pOTF9UjbmD2Q','2022-03-19 16:34:46','2022-03-19 16:39:46','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"user1\",\"aud\":[\"java.util.Collections$SingletonList\",[\"messaging-client\"]],\"nbf\":[\"java.time.Instant\",1647678886.087000000],\"scope\":[\"java.util.Collections$UnmodifiableSet\",[\"message.read\",\"message.write\"]],\"iss\":[\"java.net.URL\",\"http://auth-server:9000\"],\"exp\":[\"java.time.Instant\",1647679186.087000000],\"iat\":[\"java.time.Instant\",1647678886.087000000]},\"metadata.token.invalidated\":false}','Bearer','message.read,message.write',NULL,NULL,NULL,NULL,'4SDVDnfke-my1OnfMjFIFyTiIgkBARhZnSN110tCCxB1Jj8wrwnsfKolsi8UwYkOYgMH_DXObXIJfyBg1YNsDS12clbCZd5bfZDgI27msY3G7YoPwZ6DDlO_yT7SDGxE','2022-03-19 16:34:46','2022-03-19 17:34:46','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','2022-03-19 16:34:46','2022-03-19 16:34:46');

/*Table structure for table `oauth2_authorization_consent` */

DROP TABLE IF EXISTS `oauth2_authorization_consent`;

CREATE TABLE `oauth2_authorization_consent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `principal_name` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `created_at` datetime NOT NULL,
  `authorities` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_client_id_princle_name` (`client_id`,`principal_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `oauth2_authorization_consent` */

insert  into `oauth2_authorization_consent`(`id`,`client_id`,`principal_name`,`created_at`,`authorities`,`updated_at`) values (6,'client-1','user1','2022-03-18 22:10:56','SCOPE_message.write','2022-03-18 22:10:56');

/*Table structure for table `oauth2_client` */

DROP TABLE IF EXISTS `oauth2_client`;

CREATE TABLE `oauth2_client` (
  `id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `client_id` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '客户端id，唯一',
  `client_id_issued_at` datetime NOT NULL,
  `client_secret` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `client_secret_expires_at` datetime DEFAULT NULL,
  `client_name` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `client_icon` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应用图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `oauth2_client` */

insert  into `oauth2_client`(`id`,`client_id`,`client_id_issued_at`,`client_secret`,`client_secret_expires_at`,`client_name`,`client_icon`) values ('client-1','messaging-client','2022-03-17 21:43:36','{noop}secret','2022-03-17 21:43:47','测试',NULL);

/*Table structure for table `oauth2_client_authentication_method` */

DROP TABLE IF EXISTS `oauth2_client_authentication_method`;

CREATE TABLE `oauth2_client_authentication_method` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `method` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '认证方法',
  PRIMARY KEY (`id`),
  KEY `idx_client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `oauth2_client_authentication_method` */

insert  into `oauth2_client_authentication_method`(`id`,`client_id`,`method`) values (1,'messaging-client','client_secret_basic'),(2,'messaging-client','basic'),(3,'messaging-client','post'),(4,'messaging-client','client_secret_post'),(5,'messaging-client','client_secret_jwt'),(6,'messaging-client','private_key_jwt'),(7,'messaging-client','none');

/*Table structure for table `oauth2_client_authentication_scope` */

DROP TABLE IF EXISTS `oauth2_client_authentication_scope`;

CREATE TABLE `oauth2_client_authentication_scope` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `scope` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT 'scope',
  PRIMARY KEY (`id`),
  KEY `idx_client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `oauth2_client_authentication_scope` */

insert  into `oauth2_client_authentication_scope`(`id`,`client_id`,`scope`) values (1,'messaging-client','message.read'),(2,'messaging-client','message.write');

/*Table structure for table `oauth2_client_authorization_grant_type` */

DROP TABLE IF EXISTS `oauth2_client_authorization_grant_type`;

CREATE TABLE `oauth2_client_authorization_grant_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `grant_type` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '认证方法',
  PRIMARY KEY (`id`),
  KEY `idx_client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `oauth2_client_authorization_grant_type` */

insert  into `oauth2_client_authorization_grant_type`(`id`,`client_id`,`grant_type`) values (1,'messaging-client','authorization_code'),(2,'messaging-client','refresh_token'),(3,'messaging-client','client_credentials'),(4,'messaging-client','password');

/*Table structure for table `oauth2_client_redirect_uri` */

DROP TABLE IF EXISTS `oauth2_client_redirect_uri`;

CREATE TABLE `oauth2_client_redirect_uri` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `redirect_uri` varchar(500) COLLATE utf8mb4_bin NOT NULL COMMENT 'scope',
  PRIMARY KEY (`id`),
  KEY `idx_client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Data for the table `oauth2_client_redirect_uri` */

insert  into `oauth2_client_redirect_uri`(`id`,`client_id`,`redirect_uri`) values (1,'messaging-client','http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc'),(2,'messaging-client','http://127.0.0.1:8080/authorized'),(3,'messaging-client','http://www.baidu.com');

/*Table structure for table `oauth2_client_setting` */

DROP TABLE IF EXISTS `oauth2_client_setting`;

CREATE TABLE `oauth2_client_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(60) NOT NULL COMMENT 'client.client_id',
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `oauth2_client_setting` */

insert  into `oauth2_client_setting`(`id`,`client_id`,`name`,`value`,`created_at`) values (1,'messaging-client','settings.client.require-authorization-consent','true','2022-03-17 21:06:06'),(2,'messaging-client','settings.client.require-proof-key','false','2022-03-17 23:15:34');

/*Table structure for table `oauth2_token_setting` */

DROP TABLE IF EXISTS `oauth2_token_setting`;

CREATE TABLE `oauth2_token_setting` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` varchar(60) NOT NULL COMMENT 'client.client_id',
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `oauth2_token_setting` */

insert  into `oauth2_token_setting`(`id`,`client_id`,`name`,`value`,`created_at`) values (1,'messaging-client','settings.token.reuse-refresh-tokens','true','2022-03-17 23:49:34'),(2,'messaging-client','settings.token.id-token-signature-algorithm','RS256','2022-03-17 23:49:57'),(3,'messaging-client','settings.token.access-token-time-to-live','300','2022-03-17 23:50:29'),(4,'messaging-client','settings.token.refresh-token-time-to-live','3600','2022-03-17 23:50:38');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
