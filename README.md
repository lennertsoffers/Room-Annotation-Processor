# Room's annotation processor

Welcome to the README file of my research assignment of Android Application development.

My project can be found in [this article](./markdown/The%20magic%20of%20Room's%20annotation%20processor.md).

## Topic

For this research assignment, I wanted to do something different than just making a plain application. I chose to write a research about annotation processing. With a focus on the annotation processor that Room uses to generate files that handle database interaction in SQLite.

The research consists of two parts: 
* The research documents consisting of 5 chapters that explain a lot of theoretical concepts using code snippets

  [Link to the introduction document](./markdown/The%20magic%20of%20Room's%20annotation%20processor.md)

* Two demo applications, one made with Room and one made with a self-written annotation processor

You learn a lot by creating applications using concepts, but you learn even more when you try to explain the concepts you learned during the journey. Thats why I chose the methodology of of keep the demo simple and focussing on a wel written documentation that covers the technologies used in the demo applications.

## Planning

I started with the topic I already knew, creating an android application with navigation and fragments that uses Room as a database. Then I wanted to create the same application without Room, but without writing the SQLite implementation myself. That's why I needed an annotation processor.

To create the customized annotation processor, I needed to learn how to work with SQLite, so I followed the Android documentation tutorial for this.

Then I could do some research on how to create an annotation processor and generate source files. It is possible to create source files without any template engine, but I found it more handy to work with templates.

Template engine selection included listing the possible options, and then choosing the best option. I would have chosen Thymeleaf if it was possible to generate plain text files with it, but sadly enough it is optimize for generating xml code. That's why I chose Apache Velocity which is a super easy template engine I fell in love with during the process of using it.

Now I had to bring everything I learned together and recreate the same demo application with the annotation processor.

The final step was to document everything and explain the different parts of the research in detail.

## Time spent

![Work Schema](./markdown/images/work_schema.png)

## Overall feeling

Looking back, I'm very proud on the project I made. Its maybe not the most glamourous with the best UI, but in my opinion, the beauty can be found in understanding hard topics.

I'm really proud I was able to create a annotation processor that makes life easier. Just being able to add annotations in you code and have a working database interaction is for me personally why I love Java (and Kotlin).

But I'm also happy that I now finally understand what these annotations really do and what happens in the background. I how that people that are interested and read my research think the same.

The project may be a bit different than your standard application, but I tried my best to meet the requirements, while still keeping the demo clean and easily understandable.

That's why I would score my project a 9/10.