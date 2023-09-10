import React, {useState, useEffect, useRef, useCallback} from "react";
import { Link, useLocation } from "react-router-dom";
import Category from "../../components/repeat_etc/Category.js";

import "../../css/study_css/MyOpenStudy.css";
import Header from "../../components/repeat_etc/Header";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import LikeButton from "../../components/repeat_etc/LikeButton";
import ScrapButton from "../../components/repeat_etc/ScrapButton";
import Paging from "../../components/repeat_etc/Paging";
import Pagination from "../../css/study_css/Pagination.css";
import axios from "axios";

const MyOpenStudy = ({ sideheader }) => {
	// const dataId = useRef(1);
	const [studies, setStudies] = useState([]);
	const [scrapStates, setScrapStates] = useState(studies.scrap);
	const [likeStates, setLikeStates] = useState(studies.like);



	const location = useLocation();
	const studyState = location.state;
	const [studiesChanged, setStudiesChanged] = useState(false);

	const accessToken = localStorage.getItem('accessToken');
	//TODO MyOpenStudy 서버 전달
	useEffect(() => {
		if (studiesChanged) {
			const response = axios.post("url", {
					"study": studies,
					"scrap": scrapStates,
					"like": likeStates,
				},
				{
					withCredentials: true,
					headers: {
						'Authorization': `Bearer ${accessToken}`
					}
				})
				.then((res) => {
					console.log("전송 성공");
					console.log(res.data);
					//성공하면
					// navigate("/myopenstudy", {state: formData});

				}).catch((error) => {
					console.log('전송 실패', error);
				})
			console.log("response : ", response);
			e.preventDefault();

			localStorage.setItem("studies", JSON.stringify(studies));
			localStorage.setItem("ScrapStudies", JSON.stringify(scrapStates));
			localStorage.setItem("LikeStates", JSON.stringify(likeStates));
			// Reset studiesChanged to false
			setStudiesChanged(false);
		}
	}, [studiesChanged, studies, scrapStates, likeStates]);

//TODO MyOpenStudy 데이터 받아오기
	useEffect(() => {
		const storedStudies = axios.get("url")
			.then((res) => {
				console.log("전송 성공");
				console.log(res.data);
				setStudies(res.data.content);
				console.log(res.data.content);

				setStudies(JSON.parse(storedStudies));
			})
			.catch((error) => {
				console.error("데이터 가져오기 실패:", error);
			});
		// const storedStudies = localStorage.getItem("studies");

	}, []);
	const toggleScrap = (index) => {
		setStudies((prevStudies) => {
			const newStudies = [...prevStudies];
			newStudies[index] = { ...newStudies[index], scrap: !newStudies[index].scrap };
			setStudiesChanged(true); // Mark studies as changed
			return newStudies;
		});
	};

	const toggleLike = (index) => {
		setStudies((prevStudies) => {
			const newStudies = [...prevStudies];
			newStudies[index] = { ...newStudies[index], like: !newStudies[index].like };
			setStudiesChanged(true); // Mark studies as changed
			return newStudies;
		});
	};

	const mypartistudylist = () => {
		return (
			<div className="study_list">
				{studies.map((d ,index) => (
					<div className="list" key={d.id}>

							<div className="list_header">
								<div className="list_sub_header">
									<div className="list_day">
										{d.id}일간의 우주여행
									</div>
									{d.current==="Recruiting"?(
										<div className="list_status">모집중</div>
									):(<div className="list_status">진행중</div>)}

								</div>
								<div className="list_btn">
									<div className="list_like">
										<LikeButton like={studies[index].like}
													onClick={() => toggleLike(index)}/>
									</div>
									<div className="list_scrap">
										{/* 스크랩 버튼을 클릭하면 해당 스터디 리스트 항목의 스크랩 상태를 토글 */}
										<ScrapButton scrap={studies[index].scrap}
													 onClick={() => toggleScrap(index)}/>
									</div>
								</div>
							</div>
						<Link
							to={`/studydetail/${d.id}`}
							style={{
								textDecoration: "none",
								color: "inherit",
							}}
						>
							<div className="list_deadline">
								마감일 | {d.deadline}
							</div>
							<div className="list_title">{d.title}</div>
							<div className="list_tag">{d.field}</div>
							<div className="list_onoff">{d.onoff}</div>
							<div className="stroke"></div>
							<div className="list_founder">{d.author}</div>
						</Link>
					</div>
				))}
			</div>
		);
	};
	return (
		<div>
			<Header showSideCenter={true}/>
			<div className="container">
				<Category />
				<div className="main_container">
					<h2>스터디 개설 내역</h2>
					<div className="content_container">
						{mypartistudylist()}
					</div>
				</div>

			</div>
			<div className={"paging"}>
				<Paging/>
			</div>
		</div>
	);
};
export default MyOpenStudy;
