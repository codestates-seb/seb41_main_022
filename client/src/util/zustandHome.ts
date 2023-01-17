import create from "zustand";
import axios from "axios";

import { AxiosData } from "../components/homepage/RecruitmentList";

interface Home {
  tags: string;
  filter: string;
  search: string;
  fetch: (url: string) => any;
}

const HomeStore = create<Home>()((set) => ({
  tags: "",
  setTags: (tag: string) => {
    set((state) => ({ tags: tag }));
  },
  search: "",
  setSearch: (search: string) => {
    set((state) => ({ search: search }));
  },
  filter: "",
  setFilter: (filter: string) => {
    set((state) => ({ filter: filter }));
  },
  fetch: async (url) => {
    try {
      await axios
        .get(
          url +
            `?page={1}&size={10}&search={Loud}&filter={random}&tags={IT,수학}`
        )
        .then((res) => alert(JSON.stringify(res)));
    } catch (e) {
      console.log(e);
    }
  },
}));

export default HomeStore;
