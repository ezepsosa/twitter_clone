import { TweetRequest } from "../../../components/types";

export interface InnerContainerProps {
  $justifycontent?: string;
  $padding?: string;
  $alignitems?: string;
  $margin?: string;
}

export interface ImageProps {
  src: string;
}

export interface InputTextProps {
  $scrollHeight: number;
}

export interface interfacePostProps {
  addPostAndUpdateLocalList: (Post: TweetRequest) => Promise<true | undefined>;
}
