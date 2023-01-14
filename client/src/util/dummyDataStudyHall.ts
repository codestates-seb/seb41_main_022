const StudyHallData = {
  data: {
    teamName: "Your Study",
    status: "가입",
    isHost: true,
    summary:
      "집주변 카페에 모여 토론하는 스터디입니다! 가끔 인스타 핫카페에서 친목도 해요",
    content:
      "Our study is an 80-year-old coding study. Lets all work hard. Baekjun is solving the problem with JavaScript. I do five questions a week and submit them to GitHub. Thats it for the introduction. Bye!",
  },
};

const CommentCreate = {
  data: {
    chatId: 1,
    chatUserId: "0",
    content: "저는 민트가 좋아요",
    isClosedChat: true,
    answers: [],
  },
};

const CommentsData = {
  data: [
    {
      chatId: 2,
      chatUserId: "leewang31",
      content: "이 스터디에 김현우님 계신가요?????",
      isClosedChat: false,
      answers: [
        {
          answerId: 1,
          answerUserId: "vinyangda",
          content: "최고 수령동지 이야기 하시는 거 맞으신가요?",
        },
        {
          answerId: 2,
          answerUserId: "leewang31",
          content: "네네 그분이요",
        },
      ],
    },
    {
      chatId: 1,
      chatUserId: "kung036",
      content: "이 스터디는 몇시까지 하나요?",
      isClosedChat: false,
      answers: [
        {
          answerId: 3,
          answerUserId: "jackcmh1",
          content: "오후 5시까지 운영합니다 ㅎㅎ",
        },
        {
          answerId: 3,
          answerUserId: "kung036",
          content: "감삼다 ㅎㅎ",
        },
      ],
    },
    {
      chatId: 1,
      chatUserId: "vinyangda",
      content: "여러분 사랑해요",
      isClosedChat: true,
      answers: [
        {
          answerId: 3,
          answerUserId: "kimhw7",
          content: "엇 저는 아닌데...",
        },
        {
          answerId: 3,
          answerUserId: "vinyangda",
          content: "아하....",
        },
      ],
    },
  ],
  pageInfo: {
    page: 1,
    size: 15,
    totalElements: 3,
    totalPages: 1,
  },
};

const Reply = {
  data: {
    content: "저는 민트가 좋아요",
    isClosedChat: true,
  },
};

export default StudyHallData;
export { CommentCreate, CommentsData, Reply };
