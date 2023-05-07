import axios from "axios";
import React, { useState, useEffect } from "react";
export const getAllIdea = async () => {
    axios
      .get("https://localhost:8998/GetAllIdea")
      .then((res) => {
        return res.data.mData;
      })
      .catch((err) => {
        console.log(err);
      });
};
export const login = async (mycredential) => {
  console.log("Yes");
  try {
    const response = await axios.post(
      "https://2023sp-team-m.dokku.cse.lehigh.edu/login",
      //"https://localhost:8998/login",
      mycredential.credential
    );
    const responseData = response.data;
    console.log("Received data: ", responseData);
  } catch (error) {
    console.log(error);
  }
};

export const getIdea = async (IdeaId,SessID) => {
  axios
    .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/GetIdea/${IdeaId}/${SessID}`)
    .then((res) => {
      console.log(res.data.mData);
      return res.data.mData;
    })
    .catch((err) => {
      console.log(err);
    });
};

export const insertIdea = async (SessID) => {
  axios
    .post(`https://2023sp-team-m.dokku.cse.lehigh.edu/insertIdea/${SessID}`)
    .then((res) => {
      console.log(res.data.mData);
      return res.data.mData;
    })
    .catch((err) => {
      console.log(err);
    });
};

export const updateComment = async (id, IdeaId, SessID) => {
  axios
    .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/insertComment/${id}/${IdeaId}/${SessID}`)
    .then((res) => {
      console.log(res.data.mData);
      return res.data.mData;
    })
    .catch((err) => {
      console.log(err);
    });
};

export const likeidea = async (Ideaid, SessID) => {
    axios
      .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/likeIdea/${Ideaid}/${SessID}`)
      .then((res) => {
        console.log(res.data.mData);
        return res.data.mData;
      })
      .catch((err) => {
        console.log(err);
      });
};

export const unlikeidea = async (Ideaid, SessID) => {
    axios
      .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/unlikeIdea/${Ideaid}/${SessID}`)
      .then((res) => {
        console.log(res.data.mData);
        return res.data.mData;
      })
      .catch((err) => {
        console.log(err);
      });
};

export const likecomment = async (Ideaid, id, SessID) => {
    axios
      .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/likeComment/${Ideaid}/${id}/${SessID}`)
      .then((res) => {
        console.log(res.data.mData);
        return res.data.mData;
      })
      .catch((err) => {
        console.log(err);
      });
};

export const unlikecomment = async (Ideaid, id, SessID) => {
    axios
      .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/unlikeComment/${Ideaid}/${id}/${SessID}`)
      .then((res) => {
        console.log(res.data.mData);
        return res.data.mData;
      })
      .catch((err) => {
        console.log(err);
      });
};

export const getprofile = async (SessID, User) => {
    axios
      .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/getProfile/${SessID}/${User}`)
      .then((res) => {
        console.log(res.data.mData);
        return res.data.mData;
      })
      .catch((err) => {
        console.log(err);
      });
};

export const updateprofile = async (SessID, name, email, genId, sexOtn, note) => {
    axios
      .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/profile/${SessID}/${name}/${email}/${genId}/${sexOtn}/${note}`)
      .then((res) => {
        console.log(res.data.mData);
        return res.data.mData;
      })
      .catch((err) => {
        console.log(err);
      });
};

export const insertMedia = async (IdeaId, MediaLink) => {
  axios
    .get(`https://2023sp-team-m.dokku.cse.lehigh.edu//insertComment/${IdeaId}/${MediaLink}`)
    .then((res) => {
      console.log(res.data.mData);
      return res.data.mData;
    })
    .catch((err) => {
      console.log(err);
    });
};

export const GetComment = async (IdeaId) => {
  axios
    .get(`https://2023sp-team-m.dokku.cse.lehigh.edu/GetComment/${IdeaId}`)
    .then((res) => {
      console.log(res.data.mData);
      return res.data.mData;
    })
    .catch((err) => {
      console.log(err);
    });
};