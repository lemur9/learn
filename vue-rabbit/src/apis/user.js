import httpInstance from "@/utils/http";

export function loginAPI({ account, password }) {
  return httpInstance({
    url: "/login",
    method: "POST",
    data: {
      account,
      password,
    },
  });
}

export function getLikeListAPI({ limit = 4 }) {
  return httpInstance({
    url: "/goods/relevant",
    params: {
      limit,
    },
  });
}
