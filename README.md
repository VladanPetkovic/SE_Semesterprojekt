# Monster Trading Cards Game (MTCG)

Author: Vladan Petkovic

Winter semester 2023

Submit-date: 07.01.2024

---
### Project summary

> This card-game is written in Java and developed throughout the course of 
> Software Engineering 1 in the 3rd Semester - Bachelor of Computer Science.

University of Applied Sciences Technikum Wien

---
### Project access


Visit: 
https://github.com/VladanPetkovic/SE_Semesterprojekt.git to check my project on github.

---
### Legal issue
> Please note, that the curlScripts have been provided by the University of Applied
> Sciences Technikum Wien and were only adapted by myself
---
### Project structure

![UML_diagram](src/main/java/org/example/frontend/uml.png)

---

### Database structure
![ER_diagram](src/main/java/org/example/backend/daos/ER_diagram.png)

> note, that "log VARCHAR(10000)" is only used for testing and not needed
> for the project requirements
---
## GIT Commands
some Commands needed during the project:

| command:                      | meaning                                                                                                                              |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| git status                    | List which files are staged, unstaged, and untracked.                                                                                |
| git init                      | initialize an existing directory as a Git repository                                                                                 |
| git add "filename"            | add file to project                                                                                                                  |
| git add --all                 | add all changes or files                                                                                                             |
| git commit -m "Some comment"  | commit your changes                                                                                                                  |
| git push -u origin main       | push to origin                                                                                                                       |
| git push origin <branch_name> | push to a repository                                                                                                                 | 
| git branch <branch_name>      | create new branch                                                                                                                    |
| git branch                    | show branches                                                                                                                        |
| git branch <branch_name> -D   | delete branch                                                                                                                        |
| git pull <remote>             | Fetch the specified remote’s copy of current branch and immediately merge it into the local copy                                     |
| git fetch <remote>            | get only metadata WITHOUT file transfer                                                                                              |
| git checkout <branch_name>    | switch to a branch                                                                                                                   |
| git merge <branch_name>       | merge a branch into the current branch                                                                                               |
| git clone [url]               | retrieve an entire repository from a hosted location via URL                                                                         |
| git diff                      | Show unstaged changes between your index and working directory.                                                                      |
| git log -<limit>              | Limit number of commits by <limit>. E.g. ”git log -5” will limit to 5 commits.                                                       |
| git reset <file>              | Remove <file> from the staging area, but leave the working directory unchanged. This unstages a file without overwriting any changes |
