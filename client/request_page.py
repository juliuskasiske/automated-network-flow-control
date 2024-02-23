import streamlit as st
from crew_client import get_meta_data, request_track_warrant, get_itinerary
from client_classes import Job, TrackWarrant, Milepost
import controller

meta_data = dict(get_meta_data())
jobs = meta_data["jobIds"]
nodes = meta_data["nodes"]

returned_track_warrant = None

st.title("Track Warrant")

with st.expander("Job Information", True):
    st.subheader("Job Information")
    selected_job_number = st.selectbox("Select Your Job", options=jobs)
    selected_length = st.number_input("Specify your train length in meters", 
                                        min_value=0,
                                        max_value=10,
                                        value=0)
    name = st.text_input("State your Full Name", max_chars=30)

    saved = st.button("Save Job Information", key = "save-job-info")
    job = Job(selected_job_number, name)
    if saved:
        job = Job(selected_job_number, name)
        st.success("Job Info Saved Successfully")

with st.expander("Request Track Warrant", True):
    st.subheader("Request Track Warrant")
    selected_origin = st.selectbox("Origin", nodes)
    selected_destination = st.selectbox("Destination", nodes)

    demand = st.button("Request Track Warrant", key = "request-track-warrant")
    if demand:
        trackwarrant = TrackWarrant(Milepost(selected_origin), 
                                    Milepost(selected_destination), job)
        returned_track_warrant = request_track_warrant(trackwarrant)
        controller.persist_track_warrant_data({"liveTrackWarrant": returned_track_warrant,
                                                "position": selected_origin})

with st.expander("Live Track Warrant", True):
    st.subheader("Live Track Warrant")
    current_track_warrant = controller.read_track_warrant_data()["liveTrackWarrant"]
    if current_track_warrant is None:
        st.warning("Request track warrant first")
    else:
        try:
            if current_track_warrant["status"] == "DENIED":
                st.error("Track arrant denied - query again soon!")
            else:
                st.success("You have a live track warrant")
                origin = controller.read_track_warrant_data()["position"]
                destination = current_track_warrant["destination"]["milepostNumber"]
                st.write("Valid from: " + f"**{origin}**")
                st.write("Valid to: " + f"**{destination}**")
                itinerary = get_itinerary(origin, destination)
                selected_position = st.select_slider("Update Position", options=itinerary)
                if st.button("Report New Position", "report-position"):
                    data = controller.read_track_warrant_data()
                    data["position"] = selected_position
                    controller.persist_track_warrant_data(data) 

        except TypeError as err:
            st.error("HTTP error occured with status code: " + str(current_track_warrant))
            st.write(trackwarrant.to_dict())













