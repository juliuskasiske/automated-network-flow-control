import streamlit as st
from crew_client import get_meta_data, request_track_warrant
from client_classes import Job, TrackWarrant, Milepost

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

with st.expander("Live Track Warrant"):
    st.subheader("Live Track Warrant")
    if returned_track_warrant == None:
        st.warning("Request track warrant first")
    else:
        try:
            if returned_track_warrant["status"] == "DENIED":
                st.error("Track arrant denied - query again soon!")
            else:
                st.success("You have a live track warrant")
                origin = returned_track_warrant["origin"]["milepostNumber"]
                destination = returned_track_warrant["destination"]["milepostNumber"]
                st.write("Valid from: " + f"**{origin}**")
                st.write("Valid from: " + f"**{destination}**")
                st.write(returned_track_warrant)
        except TypeError as err:
            st.error("HTTP error occured with status code: " + str(returned_track_warrant))
            st.write(trackwarrant.to_dict())













