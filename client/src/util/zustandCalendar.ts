import create from "zustand";
import axios from "axios";

interface calendarState {
  calendarPatch: (url: string, id: string, data: object) => void;
}
export const calendarStore = create<calendarState>((set) => ({
  calendarPatch: (url, id, data) => {
    try {
      axios
        .post(url + "/calendar?studyId=" + id, data)
        .then((res) => console.log(res));
    } catch (error) {
      console.log("err", error);
    }
  },
}));
