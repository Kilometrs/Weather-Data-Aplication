# Weather Data Aplication
## Description
Weather Data Aplication is a Java based application, that retrieves latest weather infromation about different locations from different weather forecast services. This information is then shown in the UI for better accessibility. Retrieved data is also saved to a database and can be accessed in the same UI to have an overlook of data retrieved before for comparing.

## Usage
1. Have a network connection;
2. Run the app.

## Data sources
Application does not use any local sources (aside database hosted locally while developing).
Therefore, sources are:
- websites, that are being scraped and respective data being used;
- database, that contains already recorded data;

Settings and variables are stored in their respective class files.

## Results
Results retrieved from the services are showed numerically in the UI. Information is displayed in several panels based on contex (as site results, average and history). 
The retrieved results (from these forecast services) are saved in a database in two different forms. The forms are:
- raw form, which is data directly from sources;
- average form, which is calculated city specific raw data. 
If new city or source is detected, it is also saved to a database as an unique identifier.

## Conclusions and progress during the course
The development of this coursework was started later than comfortable, which was mostly due to my own personal burnout. But despite this, I managed to write it and I would personally say, that I have done a pretty okay job (7/10).
There are many aspects that could be upgraded, especially the way, how the code is organised the GUI code elements. 
During the development, the basic idea of data retrieval was rewritten 3 times as I could not fully imagine the ammount of data that I would need to be working with. Despite that, it made me fully grasp the concept of object realtions and their implementation. 
The development of GUI was much faster than the last time as I knew what to do and how most of the elements worked.
In conclusion I would say, that I learned implementation of certain features in Java (especially object relation, database integration as well as web scraping), but there is more room to grow to make it even better and code more readable. For the next project I will try to plan ahead so that I do not have to rewrite the code as I had here.

# Development
## Known bugs
- does not refresh data after Source comboBox is activated;

## To-Add
- loading screen;
- safety system if there is no internet;

## To-Fix
- history table going sicko mode;