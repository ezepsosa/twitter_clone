import { Tweet, User } from "./types";
import axios, { AxiosResponse } from "axios";

const endpoint = `http://localhost:8080/api/v1/`;

// Función para obtener usuarios
export async function getUsers(): Promise<User[]> {
  try {
    const res: AxiosResponse<User[]> = await axios.get(endpoint + "user/all");
    return res.data; // Devuelve la lista de usuarios
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to fetch users",
        statusText: error.response?.statusText || "Network Error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}

// Función para obtener posts
export async function getPosts(): Promise<Tweet[]> {
  try {
    const res: AxiosResponse<Tweet[]> = await axios.get(endpoint + "post/all");
    return res.data; // Devuelve la lista de posts
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw {
        message: "Failed to fetch posts",
        statusText: error.response?.statusText || "Network Error",
        status: error.response?.status || 500,
      };
    } else {
      throw {
        message: "An unexpected error occurred",
        statusText: "Unknown Error",
        status: 500,
      };
    }
  }
}
