function routes() {
    const getAllIdea = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/GetAllIdea")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const getIdea = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/GetIdea")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const insertIdea = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/insertIdea")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const updateComment = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/updateComment")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const likeidea = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/likeIdea")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const unlikeidea = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/unlikeIdea")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const likecomment = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/likeComment")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const unlikecomment = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/unlikeComment")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const login = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/login")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const getprofile = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/getProfile")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
    const updateprofile = async () => {
        axios
          .get("http://2023sp-team-m.dokku.cse.lehigh.edu/GetAllIdea")
          .then((res) => {
            console.log(res.data.mData);
            setMessages(res.data.mData);
          })
          .catch((err) => {
            console.log(err);
          });
        console.log("Yess");
    };
    useEffect(() => {
    getMessages();
    }, []);
}
export default MessageList;