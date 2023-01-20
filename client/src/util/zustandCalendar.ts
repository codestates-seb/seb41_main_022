import create from "zustand";
import axios from "axios";

interface calendarState {
  calendarPost: (url: string, id: string, data: object) => void;
  calendarDelete: (url: string, id: string) => void;
  calendarPatch: (url: string, id: string, data: object) => void;
}
export const calendarStore = create<calendarState>((set) => ({
  calendarPost: (url, id, data) => {
    try {
      axios
        .post(url + "/calendar?studyId=" + id, data)
        .then((res) => console.log(res));
      alert("스터디가 생성되었습니다");
    } catch (error) {
      console.log("err", error);
      alert("에러");
    }
  },
  calendarDelete: (url, id) => {
    try {
      axios.delete(url + "/calendar/" + id);
      alert("삭제되었습니다");
    } catch (error) {
      console.log("err", error);
      alert("에러");
    }
  },
  calendarPatch: (url, id, data) => {
    try {
      axios
        .patch(url + "/calendar/" + id, data)
        .then((res) => console.log(res));
      alert("스터디가 수정되었습니다");
    } catch (error) {
      console.log("err", error);
      alert("에러");
    }
  },
}));
