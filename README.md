# EuBot
Small framework for automated scenario for articles publishing 
## Configuration
 - Install Maven, Git, JDK
 - Set local variables for installed tools
 - Clone this repo: git clone https://github.com/RockChild/EuBot.git (link is in the top right corner)
 - Run/configure a Windows sceduled task to run start.bat

## How it works
 - test method runs the script against every article stored in data.json and stores results in log.txt
 - data.json consists of articles data, which you can change, but for now every line should be in one line
 - log.txt - stores results after every run
 - start.bat - batch file for running bot, which stores your local changes to your local git repo, commit it and pull the changes from remote. It was created for scheduling a Winfows task
