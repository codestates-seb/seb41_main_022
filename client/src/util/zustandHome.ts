import create from "zustand";
import axios from "axios";

interface Home {
  tags: string;
  filter: string;
  search: string;
  recruitmentData: undefined | Recruitment[];
  fetch: (tags: string, filter: string, search: string) => any;
  setTags: (tag: string) => void;
  setFilter: (filter: string) => void;
  setSearch: (search: string) => void;
  setRecruitment: (data: Recruitment[]) => void;
}

interface Recruitment {
  content: string;
  dayOfWeek: string[];
  image: string;
  leaderId: number;
  notice?: null;
  openClose: boolean;
  procedure: boolean;
  requester: [];
  startDate: string;
  studyId: number;
  summary: string;
  teamName: string;
  want: number;
}

const HomeStore = create<Home>()((set) => ({
  tags: "",
  filter: "",
  search: "",
  recruitmentData: undefined,
  setRecruitment: (data: Recruitment[]) => {
    set((state) => ({ recruitmentData: data }));
  },
  fetch: async (tags: string, filter: string, search: string) => {
    try {
      const res = await axios.get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/cards?page=1&size=10&search=${search}&filter=${filter}&tags=${tags}`
      );
      set({ recruitmentData: await res.data.data });
      console.log(res.data);
    } catch (e) {
      console.log(e);
    }
  },
  setTags: (tag: string) => {
    set((state) => ({ tags: tag }));
  },
  setFilter: (filter: string) => {
    set((state) => ({ filter: filter }));
  },
  setSearch: (search: string) => {
    set((state) => ({ search: search }));
  },
}));

export default HomeStore;
