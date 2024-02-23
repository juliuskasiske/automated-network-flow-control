import streamlit as st
from crew_client import get_job_numbers, request_track_warrant
from client_classes import Job, TrackWarrant, Milepost

st.title("Track Warrant")

with st.expander("Job Information", True):
    st.subheader("Job Information")
    jobs = list(get_job_numbers())
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
    selected_origin = st.number_input("Origin - Milepost Number", 0)
    selected_destination = st.number_input("Intended Destination - Milepost Number", 0)

    demand = st.button("Request Track Warrant", key = "request-track-warrant")
    if demand:
        trackwarrant = TrackWarrant(Milepost(selected_origin), 
                                    Milepost(selected_destination), job)
        returned_track_warrant = request_track_warrant(trackwarrant)
        try:
            if returned_track_warrant["status"] == "DENIED":
                st.error("Track Warrant Denied - Query Again Soon!")
            else:
                st.write(returned_track_warrant)
        except TypeError as err:
            st.error("HTTP error occured with status code: " + str(returned_track_warrant))
            st.write(trackwarrant.to_dict())










