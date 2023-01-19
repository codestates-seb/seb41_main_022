import create from "zustand";
import axios from "axios";
interface calendarState {
  calendarGet: (url: string, id: string) => void;
}
export const calendarStore = create<calendarState>((set) => ({
  //서버에 캘린더 데이터 요청
  //서버에 수정사항 보내기
  //서버에 캘린더 삭제 요청
  calendarGet: async (url, id) => {
    try {
      await axios
        .get(url + "/calendar?studyId=" + id)
        .then((res) => console.log(res));
    } catch (error) {
      console.log(error);
    }
  },
}));
