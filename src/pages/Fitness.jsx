import React, { useState } from 'react';
import { motion } from 'framer-motion';
import StatCard from '../components/StatCard';
import ProgressCircle from '../components/ProgressCircle';

const Fitness = () => {
  const [workouts] = useState([
    { name: 'Running', progress: 75, icon: '🏃' },
    { name: 'Weight Training', progress: 68, icon: '🏋️' },
    { name: 'Yoga', progress: 82, icon: '🧘' },
    { name: 'Cardio', progress: 70, icon: '❤️' },
  ]);

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-6xl mx-auto">
        <motion.h1
          className="text-4xl font-bold mb-2 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          💪 Fitness Tracker
        </motion.h1>
        <p className="text-gray-400 mb-8">Track your health & fitness goals</p>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <StatCard title="Calories Burned" value="2450" icon="🔥" />
          <StatCard title="Distance" value="42km" icon="📍" />
          <StatCard title="Workouts" value="8" icon="✅" />
          <StatCard title="Hours" value="15.5" icon="⏱️" />
        </div>

        <h2 className="text-2xl font-bold mb-6 text-white">Workout Progress</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {workouts.map((workout, index) => (
            <ProgressCircle
              key={index}
              label={workout.name}
              percentage={workout.progress}
              icon={workout.icon}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Fitness;