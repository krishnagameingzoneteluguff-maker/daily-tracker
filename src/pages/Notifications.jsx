import React, { useState } from 'react';
import { motion } from 'framer-motion';

const Notifications = () => {
  const [notifications] = useState([
    { type: 'success', title: '🔥 Streak Alert!', message: 'You\'ve maintained a 12-day streak!', time: '2 hours ago' },
    { type: 'info', title: '⏰ Time to Focus', message: 'Your scheduled focus session starts in 5 minutes', time: '1 hour ago' },
    { type: 'warning', title: '📚 UPSC Update', message: 'You\'ve completed 70% of today\'s UPSC studies', time: '3 hours ago' },
    { type: 'success', title: '💪 Workout Complete', message: 'Great job! You completed your fitness routine', time: '5 hours ago' },
    { type: 'info', title: '💻 Coding Challenge', message: 'New daily coding challenge available!', time: '8 hours ago' },
    { type: 'success', title: '✅ Task Completed', message: '15 tasks completed today. Keep it up!', time: '10 hours ago' },
  ]);

  const getNotificationColor = (type) => {
    switch(type) {
      case 'success': return 'border-green-400/30 bg-green-400/5';
      case 'warning': return 'border-yellow-400/30 bg-yellow-400/5';
      case 'info': return 'border-blue-400/30 bg-blue-400/5';
      default: return 'border-cyan-400/30 bg-cyan-400/5';
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-3xl mx-auto">
        <motion.h1
          className="text-4xl font-bold mb-8 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          🔔 Notifications
        </motion.h1>

        <div className="space-y-4">
          {notifications.map((notif, idx) => (
            <motion.div
              key={idx}
              initial={{ opacity: 0, x: -20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: idx * 0.05 }}
              className={`border rounded-lg p-4 ${getNotificationColor(notif.type)}`}
            >
              <div className="flex justify-between items-start">
                <div>
                  <p className="text-white font-bold text-lg">{notif.title}</p>
                  <p className="text-gray-400 mt-1">{notif.message}</p>
                </div>
                <p className="text-gray-500 text-sm whitespace-nowrap ml-4">{notif.time}</p>
              </div>
            </motion.div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Notifications;