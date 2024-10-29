// axios基础的封装
import router from "@/router";
import { useUserStore } from "@/stores/userStore";
import axios from "axios";
import { ElMessage } from "element-plus";

const httpInstance = axios.create({
  baseURL: "https://pcapi-xiaotuxian-front-devtest.itheima.net",
  timeout: 5000,
});

// 拦截器

// axios请求拦截器
httpInstance.interceptors.request.use(
  (config) => {
    const userStore = useUserStore();
    const token = userStore.userInfo.token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (e) => Promise.reject(e)
);

// axios响应式拦截器
httpInstance.interceptors.response.use(
  (res) => res.data,
  (e) => {
    const userStore = useUserStore();
    ElMessage({
      type: "warning",
      message: e.response.data.message,
    });

    if (e.response.status === 401) {
      userStore.clearUserInfo();
      router.replace({ path: "/login" });
    }
    return Promise.reject(e);
  }
);

export default httpInstance;
