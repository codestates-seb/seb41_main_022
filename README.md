## 🌳 Study Tree 

### 🥳 스터디 모집은 Study Tree와 함께!
![image](https://raw.githubusercontent.com/codestates-seb/seb41_main_022/bd5e2ae1f70f11a9eb09c199a174ebab84e6575f/client/src/assets/readme/Study%20Tree.svg)
---

### 개발기간: 2023.01.03 ~ 2023.02.03

---

<br>

## 🍎 Information

#### Study Tree는 쉽게 스터디를 개설하고, 마음이 맞는 이용자를 쉽게 모을수 있습니다 !

<br>

## 👨‍👩‍👧‍👦 Team Members
|             김현우(FE 팀장)              |                양다빈 (FE 팀원)                 |                             이기완 (FE 팀원)                             |               김 (BE 팀원)                |              신우경 (BE 팀원)               |               최민혁 (BE 팀원)                | 
|:-----------------------------------:|:------------------------------------------:|:-------------------------------------------------------------------:|:----------------------------------------:|:--------------------------------------:|:----------------------------------------:|
|             ![image](https://avatars.githubusercontent.com/u/100066239?s=64&v=4)              |            ![image](https://avatars.githubusercontent.com/u/110925532?s=64&v=4)             | ![image](https://avatars.githubusercontent.com/u/99003659?s=64&v=4) |           ![imgage](https://avatars.githubusercontent.com/u/110886358?s=64&v=4)           |          ![image](https://avatars.githubusercontent.com/u/80895384?s=64&v=4)           |           ![image](https://avatars.githubusercontent.com/u/110888214?s=64&v=4)            |
|[@kimhw7](https://github.com/kimhw7) | [@vinyangda](https://github.com/vinyangda) |              [@LeeWang](https://github.com/Leewang31)               | [@lisia004](https://github.com/lisia004) | [@kung036](https://github.com/kung036) | [@jackcmh1](https://github.com/jackcmh1) |


<br>

## 🌞Team Rule

- 매일 1시에 전체회의를 진행 한다.
- 프로젝트 기간 동안 평일 1시 ~ 오후 5시 사이에는 응답을 10분 이내에 하도록 한다.
- 모르면 혼자 고민하지 않고 같은 파트 팀원에게라도 우선 공유한다.
- 오버 커뮤니케이션을 지향한다.
- 정한 컨벤션을 준수한다.
- conflict 에러를 방지하고자 기능별로 작업이 끝나면 github로 push 보낸다.
- 하루 한번 이상 commit을 진행한다

<br>

## 🌟 branch
#### strategy
- main → dev → feat 순으로 브랜치 생성

#### branch types
- main: 배포 브랜치
- dev : 개발 브랜치
- feat/이니셜/branchname : 기능 브랜치 (ex - feat/khw/login)
- hotfix : 오류 또는 긴급 수정 브랜치

#### feat브랜치 name style
- 기능별 브랜치 생성, 담당자 이니셜 적기
- 기능별로 브랜치를 생성하고, 1개의 브랜치는 1명의 사용자가 담당합니다.

>feat/담당자/기능명
>
>feat/khw/login <br>
>feat/lkw/logo


## ✨Git Commit

#### structure
> 타입 - #이슈번호 : 제목<br>
> (공백줄)<br>
> 상세 설명

### type

| 태그이름 | 설명                                                  |
| -------- | ----------------------------------------------------- |
| feat | A new feature|
| fix   | A bug fix|
| docs   | Changes to documentation|
| refactor  | Refactoring production code|
| chore | Updating build tasks, package manager configs, etc; no production code change|

#### example
>feat - #1 : 로그인 html,css 완료<br>
>공통 인풋 텍스트, 버튼 컴포넌트 적용

## 🎉merge
- github pull request를 사용해 merge합니다. merge 후 브랜치는 삭제합니다.
- 개발 중에는 squash merge 배포 시에는 rebase merge 합니다.
- front개발은 최소 front 1명을 리뷰어로, back개발은 최소 back 1명을 리뷰어로 pull request 합니다.
- front, back 같이 진행하는 개발은 최소 front,back 각 1명을 리뷰어로 pull request 합니다.
