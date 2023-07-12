import { Dispatch, SetStateAction, ReactNode } from 'react';

export type ProfileDataType = {
<<<<<<< HEAD
  name: string;
  age: number;
=======
  displayName: string;
  latitude: string;
  longitude: string;
>>>>>>> 622b49e (🎨MyPage optional chaining추가)
};

export type RatingStarType = {
  ratingIndex: number;
  setRatingIndex: Dispatch<SetStateAction<number>>;
};

export type ModalType = {
  isOpen: boolean;
  setIsOpen: Dispatch<boolean>;
  children: ReactNode;
};

export type ImageUploadType = {
  input: string;
};

export type TapButtonType = {
  // nowActivatedTabValue: string;
  // handleNowActivatedTabValue: (inputValue: string) => void;
  tap: string;
  setTapList: Dispatch<SetStateAction<string[]>>;
};
export type WishListType = {
  item: string[];
};
export type PagingType = {
  currentPage: number;
  onPageChange: (page: number) => void;
  itemsPerPage: number;
  totalItemsCount: number;
};
