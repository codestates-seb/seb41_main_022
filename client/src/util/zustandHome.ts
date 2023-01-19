import create from "zustand";
import axios from "axios";

interface Home {
  tags: string;
  filter: string;
  search: string;
  recruitmentData: [] | Recruitment[];
  fetch: (
    tags: string,
    filter: string,
    search: string,
    page: number,
    recruitmentData: Recruitment[] | []
  ) => void;
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
  recruitmentData: [],
  setRecruitment: (data: Recruitment[] | []) => {
    set(() => ({ recruitmentData: data }));
  },
  fetch: async (tags, filter, search, page, recruitmentData) => {
    try {
      const res = await axios.get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/cards?page=${page}&size=12&search=${search}&filter=${filter}&tags=${tags}`
      );
      set((state) => ({
        recruitmentData: [...recruitmentData, ...res.data.data],
      }));
      console.log(res.data);
    } catch (e) {
      console.log(e);
    }
  },
  setTags: (tag: string) => {
    set(() => ({ tags: tag, recruitmentData: [] }));
  },
  setFilter: (filter: string) => {
    set(() => ({ filter: filter }));
  },
  setSearch: (search: string) => {
    set(() => ({ search: search }));
  },
}));

export default HomeStore;
