# deporte v0.0.0



- [Auth](#auth)
	- [Authenticate](#authenticate)
	
- [Deporte](#deporte)
	- [Create deporte](#create-deporte)
	- [Delete deporte](#delete-deporte)
	- [Retrieve deporte](#retrieve-deporte)
	- [Retrieve deportes](#retrieve-deportes)
	- [Update deporte](#update-deporte)
	
- [Foto](#foto)
	- [Create foto](#create-foto)
	- [Delete foto](#delete-foto)
	- [Retrieve foto](#retrieve-foto)
	- [Retrieve fotos](#retrieve-fotos)
	- [Update foto](#update-foto)
	
- [Grupo](#grupo)
	- [Create grupo](#create-grupo)
	- [Delete grupo](#delete-grupo)
	- [Retrieve public-grupo](#retrieve-public-grupo)
	- [Retrieve grupos](#retrieve-grupos)
	- [Update grupo](#update-grupo)
	- [bloquear grupo](#bloquear-grupo)
	- [desbloquear grupo](#desbloquear-grupo)
	- [exit grupo](#exit-grupo)
	- [join grupo](#join-grupo)
	
- [User](#user)
	- [Create user](#create-user)
	- [Delete user](#delete-user)
	- [Desbloquear user](#desbloquear-user)
	- [Retrieve current user](#retrieve-current-user)
	- [Retrieve user](#retrieve-user)
	- [Retrieve users](#retrieve-users)
	- [Update password](#update-password)
	- [Update user](#update-user)
	- [bloquear user](#bloquear-user)
	- [friend user](#friend-user)
	


# Auth

## Authenticate



	POST /auth

### Headers

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| Authorization			| String			|  <p>Basic authorization with email and password.</p>							|

### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>Master access_token.</p>							|

# Deporte

## Create deporte



	POST /deportes


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| nombre			| 			|  <p>Deporte's nombre.</p>							|
| descripcion			| 			|  <p>Deporte's descripcion.</p>							|
| requisitos			| 			|  <p>Deporte's requisitos.</p>							|
| foto_id			| 			|  <p>Deporte's foto_id.</p>							|

## Delete deporte



	DELETE /deportes/:id


## Retrieve deporte



	GET /deportes/:id


## Retrieve deportes



	GET /deportes


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update deporte



	PUT /deportes/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| nombre			| 			|  <p>Deporte's nombre.</p>							|
| descripcion			| 			|  <p>Deporte's descripcion.</p>							|
| requisitos			| 			|  <p>Deporte's requisitos.</p>							|
| foto_id			| 			|  <p>Deporte's foto_id.</p>							|

# Foto

## Create foto



	POST /fotos


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| url			| 			|  <p>Foto's url.</p>							|

## Delete foto



	DELETE /fotos/:id


## Retrieve foto



	GET /fotos/:id


## Retrieve fotos



	GET /fotos


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update foto



	PUT /fotos/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| url			| 			|  <p>Foto's url.</p>							|

# Grupo

## Create grupo



	POST /grupos


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id_deporte			| 			|  <p>Grupo's id_deporte.</p>							|
| titulo			| 			|  <p>Grupo's titulo.</p>							|
| descripcion			| 			|  <p>Grupo's descripcion.</p>							|
| fotos			| 			|  <p>Grupo's fotos.</p>							|
| integrantes			| 			|  <p>Grupo's integrantes.</p>							|
| localizacion			| 			|  <p>Grupo's localizacion.</p>							|
| fecha			| 			|  <p>Grupo's fecha.</p>							|
| bloqueado			| 			|  <p>Grupo's bloqueado.</p>							|

## Delete grupo



	DELETE /grupos/:id


## Retrieve public-grupo



	GET /grupos/publico/:id


## Retrieve grupos



	GET /grupos


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update grupo



	PUT /grupos/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id_deporte			| 			|  <p>Grupo's id_deporte.</p>							|
| titulo			| 			|  <p>Grupo's titulo.</p>							|
| descripcion			| 			|  <p>Grupo's descripcion.</p>							|
| fotos			| 			|  <p>Grupo's fotos.</p>							|
| integrantes			| 			|  <p>Grupo's integrantes.</p>							|
| localizacion			| 			|  <p>Grupo's localizacion.</p>							|
| fecha			| 			|  <p>Grupo's fecha.</p>							|
| bloqueado			| 			|  <p>Grupo's bloqueado.</p>							|

## bloquear grupo



	POST /grupos/bloquear/:id


## desbloquear grupo



	DELETE /grupos/desbloquear/:id


## exit grupo



	DELETE /grupos/exit/:id


## join grupo



	POST /grupos/join/:id


# User

## Create user



	POST /users


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>Master access_token.</p>							|
| email			| String			|  <p>User's email.</p>							|
| password			| String			|  <p>User's password.</p>							|
| name			| String			| **optional** <p>User's name.</p>							|
| picture			| String			| **optional** <p>User's picture.</p>							|
| role			| String			| **optional** <p>User's role.</p>							|

## Delete user



	DELETE /users/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|

## Desbloquear user



	DELETE /users/desbloquear/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|

## Retrieve current user



	GET /users/me


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|

## Retrieve user



	GET /users/:id


## Retrieve users



	GET /users


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update password



	PUT /users/:id/password

### Headers

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| Authorization			| String			|  <p>Basic authorization with email and password.</p>							|

### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| password			| String			|  <p>User's new password.</p>							|

## Update user



	PUT /users/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|
| name			| String			| **optional** <p>User's name.</p>							|
| picture			| String			| **optional** <p>User's picture.</p>							|

## bloquear user



	POST /users/bloquear/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|

## friend user



	POST /users/friend/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|


