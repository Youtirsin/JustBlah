# Note

## Techs Required

## Tasks

### Backend

* sign up 😁
* sign in  😁
* reset password 😁



* invitation send
* invitation accepted
* invitation rejected



* contact invitation
* contact delete
* contact search

####  what invitation service do

* save invitation 😁
* reject invitation (set flag 'rejected' = true)
* accept invitation (just delete it)
* load invitations
* load rejected invitations

#### what invitation controller do

* save invitation 😁
* reject invitation
* accept invitation (delete, make friend)
* load invitations
* load rejected invitations

#### invitation handle process

a send invitation to b

if b accept

​	delete invitation

​	make friend a,b

else

​	keep invitation but set flag 'rejected' = true



for user

​	when in friend invitation page

​		try to fetch invitations send by self

​	when in main page

​		try to fetch invitations send to self

#### to do

* finish invitation service and controller

## To be upgraded

* invitation send security

  client send own name and target name straightforward, which is unsecure.

## 为了软件工程作业的总结和设计

