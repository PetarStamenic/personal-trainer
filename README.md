# System Overview

The system consists of three services. These services include the User Service, which users use to log in to the system and obtain a token with which they authenticate and authorize themselves during each interaction with the system. The Training Scheduling Service allows clients to browse and search for available slots, while gym managers can enter data about the gym. There is also a Notification Service that sends an email when a training session is successfully scheduled, as well as reminders before the start of the training session.

## Features

### Service 1 (User Service):
- **User Types**:
    - The system needs to have multiple types of users with different sets of data and privileges. The data each user type should have includes: username, password, email, date of birth, first and last name. The three types of users supported by the system are: admin (no additional attributes, has all privileges, and is manually entered into the database), client (unique membership card number, number of scheduled training sessions), and gym manager (name of the gym they work at and employment date).
- **Registration**:
    - Admins do not register as they are manually entered into the database. Managers and clients have separate registration routes. After each registration, an activation email needs to be sent to confirm the registration. The email is not sent directly from this service but rather through a request to the Notification Service via a message broker to send the notification.
- **Application Usage Ban**:
    - Admins can ban a specific user from logging into the application. Additionally, at any later time, they can re-enable access for the user.
- **Login**:
    - The user submits an email and password, and if the login is successful, they receive a JWT in response.
- **Profile Editing**:
    - When a user logs into the system, they have the ability to edit all their parameters (except for the membership card number and the number of scheduled training sessions).

### Service 2 (Training Scheduling Service):
- **Editing Gym Data**:
    - The gym manager can enter and update data about the gym where they work. The data a gym can have includes name, short description, number of personal trainers, types of training available (powerlifting, pilates, calisthenics, yoga, etc.), and the price for each type of training. Training sessions need to be divided into two groups, individual and group. For example, calisthenics and powerlifting could be individual, while yoga and pilates could be group. All types of training are either individual or group.
- **Viewing Slots**:
    - Users should be able to browse available training slots that are entered into the system. It should be possible to filter by type of training, whether it is individual or group, as well as the day of the week. Sorting by training start time should also be enabled. When searching for available slots, it should be considered whether the training is group or individual. If it is individual training, only one person can book that slot, while if it is group training, up to 12 different users (or provide an option to define a different maximum number of participants when adding training to the database) can book the same slot.
- **Booking Training**:
    - Clients can book training of a specific type in a free slot. At that moment, it is necessary to asynchronously notify the Notification Service (via a message broker) to send an email confirming that the training has been successfully booked. Before calculating the price, it is necessary to fetch data from the User Service about the client's number of booked training sessions. This is fetched synchronously via an HTTP call. It is necessary to enable retrying the request if the User Service is currently unavailable (retry pattern). It is also necessary to notify the User Service that the user has booked a new training session and increase their number of booked sessions. Additionally, the available training slots in the gym should be adequately updated.
- **Canceling Booked Training**:
    - Both the client and the gym manager have the option to cancel a booked training session. It is necessary to send information (email) about the cancellation, adequately update the training slots in the gym, and notify the User Service to decrease the number of booked sessions for the client or clients whose training was canceled. Different cases of training cancellations need to be considered. If the client cancels the training, that slot should become (remain) available in the system for tracking training slots. If the manager cancels the training, that slot should remain (become) unavailable in the system for tracking training slots. If less than three different clients have booked (registered for) a specific group training slot one day (24 hours) before the start, the training session is canceled (as if the manager canceled it).
- **Defining Benefits for Loyal Clients**:
    - It is necessary to enable the gym manager to define benefits for loyal clients in the form of granting a free training session after every x booked sessions the client has made. For example, every tenth training session is free.

### Service 3 (Notification Service):
- **Defining Notification Types**:
    - Each type of notification is associated with a text and a set of parameters it receives (Hello %firstName %lastName, to verify please go to the following %link). When sending a notification, it is necessary to send the notification type, the set of parameters it receives, and the email to which the notification is sent. Only the admin can define these types. The admin also needs to be able to list, delete, and update existing notification types. For each notification mentioned below, it is necessary to define the corresponding type.
- **Sending Activation Email**:
    - The request to send the activation email goes through this service. Since the registration happens on the User Service, it is necessary to send the email data from the User Service and forward it from this service.
- **Sending Password Reset Email**.
- **Sending Notification When Training is Successfully Booked**:
    - This notification needs to be sent to the client who booked the training and the gym manager.
- **Sending Notification for Training Cancellation**:
    - This is also sent to both the client(s) and the manager.
- **Sending Reminder One Day (24 hours) Before the Training Session**.
- **Storing All Sent Notifications in the Database as an Archive**:
    - The admin can list all notifications ever sent, while the client and manager can only see those sent to them. Filtering by the following parameters needs to be provided: notification type (password change, activation email, etc.), email, and by a given time range (e.g., for the past year).