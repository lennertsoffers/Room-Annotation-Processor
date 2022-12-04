# The magic of Room’s annotation processor

## Introduction

Room is a Database Object Mapping library that enables developers to easily persist data in a database for Android development. Room uses annotation processing to generate code that handles database interaction with SQLite behind the scenes.

Room makes life easier by providing a broad set of annotations. To create a table in the database, you just have to add the @Entity annotation. To create a Data Access Object, you add the @Dao annotation.

In this research, we dive deeper in the behind the scenes of Room. We try to understand the bare bones usage of SQLite and how Room’s annotation processor generates this code for us.

## Chapters

1. [SQLite in Android](./1.%20SQLite%20in%20Android.md)

    In the first chapter, we learn how to work with SQLite in Android without the usage of Room. We learn how to setup a bare bones database interaction. We will create tables, insert data and read data.

    This chapter will make it easier to understand the code generation of Room, and of our custom annotation processor we will create.

2. [Annotations and annotation processing](./2.%20Annotations%20and%20annotation%20processing.md)

    In the second chapter, we will talk about what annotations are and what they are used for. We will lean how to define your own custom annotation. Furthermore, the topic of annotation processing is handled. This is one of the most important topics to understand the workings of Room.

3. [Setting up a database with Room](./3.%20Setting%20up%20a%20database%20with%20Room.md)

    Here, we will create an example on how to setup your own database with Room and easily create tables in it. You'll see how much afford Room does behind the scenes so you can easily setup a database without the hassle of working with plain SQLite.

4. [Room's annotation processor](./4.%20Room's%20annotation%20processor.md)

    Now we succeeded to set up a database with Room, we will look what Room did for us behind the scenes and what kind of code it generated for us.

5. [Creating a custom annotation processor](./5.%20Creating%20a%20custom%20annotation%20processor.md)

    The last chapter is about creating our own version of Room which uses annotation processing to do the SQLite interaction. Beware that this is just a demo to give a better idea on how Room does the code generation behind the scenes. This implementation is very bare bones and not recommended as a replacement for Room itself.

    Room does a lot more code generation behind the scenes and I strongly recommend to use Room for your projects!

## Wrapping up

In this research, we learned about annotation processing, code generation, Room and SQLite. Annotation processing is a tool many libraries use behind the scenes. Just think about the web framework [Spring Boot](https://spring.io).

Knowing how to use a library is one thing, but knowing what it does for you is a skill 😀