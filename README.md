# README
# Online Access
Access our app via the url:\
https://peerprep-g16.netlify.app/

# Local (dev) Access
## Backend
### 1. Set up database
1. Create a local MySQL database named `peerprep`. Make sure the database is running on `localhost` port `3306`.
Note: this operation may require root previlege.
    * In terminal, run `sudo mysql` to connect to `mysql` as root user.

    * In `mysql` console, run `create database peerprep;`
2. In `mysql` console, create a database user using the following `mysql` command:
    > `create user peerprep_admin identified by 'cs3219_G16_yyds';`
3. In `mysql` console, grant previleges to `peerprep_admin` using the following `mysql` command:
    > `grant all privileges on peerprep.* to peerprep_admin;`
4. In `mysql` console, connect to database `peerprep`:
    > `use peerprep;`
5. In our repo directory `./peerprep/src/main/resources/sql/`, look for two files `create_user_profile_table.sql` and `create_peerprep_interview_tables.sql`. Copy the SQL scripts from these two files and execute in `mysql` console.
6. So far, the tables have been set up.

### 2. Import interview question & solutions into database tables
1. In our repo directory `./peerprep/src/main/resources/data/`, look for two csv files `Copy of leetcode questions.csv`
and `Copy of leetcode solutions.csv` which contain the questions and solutions used for our application
2. Simply use the MySQL visualization tool (MySQL Workbench, phpMyAdmin, etc.) to import `Copy of leetcode questions.csv`
and `Copy of leetcode solutions.csv` into table `interview_questions` and table `interview_solutions` respectively.

### 3. Install local mail server
A local mail server is needed if you want to register a new account or reset password in our app.

Here we use `MailDev`. Open your terminal, do the following to set up MailDev:

1. Install MailDev:
    > `npm install -g maildev`
2. Run MailDev:
    > `maildev`
3. Access MailDev email portal via the link displayed on terminal. Usually it would be http://0.0.0.0:1080.
4. For registering new account in our app, you need to go to this email portal to activate your account.
5. For resetting password in our app, you need to go to this email portal to get the link for reset.

For more information about `MailDev`, you may refer to this repository: https://github.com/maildev/maildev

### 4. Download backend `jar` file
1. Download the `jar` file to an empty folder from the latest Release in our GitHub repository.
2. `cd` to the folder where the `jar` file is downloaded.

### 5. Run backend locally
Run the `jar` file with command:
> `java -jar -Dspring.profiles.active=dev <jar-file-name>`

## Frontend
// TODO