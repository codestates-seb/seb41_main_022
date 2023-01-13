const TodoListData = {
  data: [
    {
      calendarId: 1,
      title: "삼각김밥",
      start: "2023-01-03T13:45:30",
      participant: {
        1: "NONE",
      },
    },
    {
      calendarId: 2,
      title: "갈비탕탕탕수육",
      start: "2023-01-05T13:45:30",
      participant: [
        {
          userId: 1,
          name: "khw",
          joinState: "YES",
        },
        {
          userId: 2,
          name: "lkw",
          joinState: "NONE",
        },
        {
          userId: 3,
          name: "ydv",
          joinState: "No",
        },
      ],
    },
  ],
};

export default TodoListData;
